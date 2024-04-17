package controllers

import (
	"climbyAPI/inits"
	"climbyAPI/models"
	"fmt"

	"github.com/gin-gonic/gin"
)

func CreateRoute(ctx *gin.Context) {
	var body struct {
		Type          string
		Name          string
		Difficulty    string
		Images        string
		Description   string
		Location      string
		UserProfileID uint
	}

	ctx.BindJSON(&body)

	route := models.Route{
		Type: body.Type,
		Name: body.Name,
		Difficulty: body.Difficulty,
		Images: body.Images,
		Description: body.Description,
		Location: body.Location,
		UserProfileID: body.UserProfileID,
	}

	fmt.Println(route)

	result := inits.DB.Create(&route)
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": route})
}

func GetRoutes(ctx *gin.Context){
	var routes []models.Route

	result := inits.DB.Find(&routes)
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": routes})
}

func GetRoute(ctx *gin.Context){
	var route models.Route

	result := inits.DB.First(&route, ctx.Param("id"))
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": route})
}

func UpdateRoute(ctx *gin.Context){
	var body struct{
		Type          string
		Name          string
		Difficulty    string
		Images        string
		Description   string
		Location      string
	}

	ctx.BindJSON(&body)

	var route models.Route

	result := inits.DB.First(&route, ctx.Param("id"))
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	inits.DB.Model(&route).Updates(models.Route{
		Type: body.Type,
		Name: body.Name,
		Difficulty: body.Difficulty,
		Images: body.Images,
		Description: body.Description,
		Location: body.Location,
	})

	ctx.JSON(200, gin.H{"data": route})
}

func DeleteRoute(ctx *gin.Context){
	id := ctx.Param("id")

	inits.DB.Delete(&models.Route{}, id)

	ctx.JSON(200, gin.H{"data": "Route has been deleted successfully"})
}