package controllers

import (
	"climbyAPI/inits"
	"climbyAPI/models"
	"fmt"
	"strconv"

	"github.com/gin-gonic/gin"
)

func CreateComment(ctx *gin.Context){
	var body struct{
		Comment string
		UserId uint `json:"user_id"`
	}

	routeId, err := strconv.ParseUint(ctx.Param("id"), 10, 32)
	if err != nil {
		ctx.JSON(500, gin.H{"error": err.Error()})
	}
	routeIdu := uint(routeId)

	ctx.ShouldBindJSON(&body)

	user, exists := ctx.Get("user")

	if !exists {
		ctx.JSON(500, gin.H{"error": "user not found"})
		return
	}
	body.UserId = user.(models.UserProfile).ID

	comment := models.UserComments{
		Comment: body.Comment,
		UserProfileID: body.UserId,
		RouteID: routeIdu,
	}

	fmt.Println(comment)

	result := inits.DB.Create(&comment)
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": comment})
}

func GetComments(ctx *gin.Context){
	var comments []models.UserComments

	result := inits.DB.Find(&comments)
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": comments})
}

func GetComment(ctx *gin.Context){
	var comments models.UserComments

	result := inits.DB.First(&comments, ctx.Param("id"))
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": comments})
}

func UpdateComment(ctx *gin.Context){
	var body struct{
		Comment string
	}

	ctx.ShouldBindJSON(&body)

	var comment models.UserComments

	result := inits.DB.First(&comment, ctx.Param("id"))
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
	}

	inits.DB.Model(&comment).Updates(models.UserComments{
		Comment: body.Comment,
	})
	ctx.JSON(200, gin.H{"data": comment})
}

func DeleteComment(ctx *gin.Context){
	id := ctx.Param("id")

	inits.DB.Delete(&models.UserComments{}, id)

	ctx.JSON(200, gin.H{"data": "Comment has been deleted successfully"})
}
