package controllers

import (
	"climbyAPI/inits"
	"climbyAPI/models"
	"fmt"
	"net/http"
	"os"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v5"
	"golang.org/x/crypto/bcrypt"
)

func CreateProfile(ctx *gin.Context) {
	var body struct {
		Username string
		Email    string
		Bio      string
		Password string
	}

	ctx.ShouldBindJSON(&body)

	userProfile := models.UserProfile{
		Username: body.Username,
		Email:    body.Email,
		Bio:      body.Bio,
		Password: body.Password,
	}

	fmt.Println(userProfile)

	result := inits.DB.Create(&userProfile)
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": userProfile})
}

func GetUsers(ctx *gin.Context) {
	var users []models.UserProfile

	result := inits.DB.Find(&users)
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": users})
}

func GetUser(ctx *gin.Context) {
	var user models.UserProfile

	result := inits.DB.First(&user, ctx.Param("id"))
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": user})
}

func UpdateUser(ctx *gin.Context) {
	var body struct {
		Bio      string
		Username string
	}

	ctx.BindJSON(&body)

	var user models.UserProfile

	result := inits.DB.First(&user, ctx.Param("id"))
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	inits.DB.Model(&user).Updates(models.UserProfile{
		Bio:      body.Bio,
		Username: body.Username,
	})

	ctx.JSON(200, gin.H{"data": user})
}

func DeleteUser(ctx *gin.Context) {
	id := ctx.Param("id")

	inits.DB.Delete(&models.UserProfile{}, id)

	ctx.JSON(200, gin.H{"data": "User has been deleted successfully"})
}

//TODO: LikeRoute(), CompleteRoute() functions

func Signup(ctx *gin.Context) {
	var body struct {
		Username string
		Email    string
		Password string
	}

	if ctx.ShouldBindJSON(&body) != nil {
		ctx.JSON(400, gin.H{"error": "Bad request"})
	}

	hash, err := bcrypt.GenerateFromPassword([]byte(body.Password), 10)

	if err != nil {
		ctx.JSON(500, gin.H{"error": err})
		return
	}

	user := models.UserProfile{
		Username: body.Username,
		Email:    body.Email,
		Password: string(hash),
	}

	result := inits.DB.Create(&user)

	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": user})
}

func Login(ctx *gin.Context) {
	var body struct {
		Email    string
		Password string
	}

	if ctx.BindJSON(&body) != nil {
		ctx.JSON(400, gin.H{"error": "Bad request"})
		return
	}

	var user models.UserProfile

	result := inits.DB.Where("email = ?", body.Email).First(&user)

	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": "User not found"})
	}

	err := bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(body.Password))

	if err != nil {
		ctx.JSON(401, gin.H{"error": "Unauthorized"})
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"id":  user.ID,
		"exp": time.Now().Add(time.Hour * 24 * 30).Unix(),
	})

	tokenString, err := token.SignedString([]byte(os.Getenv("SECRET")))

	if err != nil {
		ctx.JSON(500, gin.H{"error": "Error signing token"})
	}

	ctx.SetSameSite(http.SameSiteLaxMode)
	ctx.SetCookie("Authorization", tokenString, 3600*24*30, "", "localhost", false, true)
	ctx.JSON(200, gin.H{"token": tokenString})
}

func Logout(ctx *gin.Context) {
	ctx.SetSameSite(http.SameSiteLaxMode)
	ctx.SetCookie("Authorization", "", -1, "", "localhost", false, true)
	ctx.JSON(200, gin.H{"data": "You are logged out!"})
}
