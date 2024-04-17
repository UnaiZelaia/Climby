package controllers

import (
	"climbyAPI/inits"
	"climbyAPI/models"
	"fmt"
	"time"

	"github.com/gin-gonic/gin"
)

func CreateClimb(ctx *gin.Context){
	var body struct{
		Date time.Time
		Comment string
		Images string
		RouteId uint
		UserId uint `json:"user_id"`
	}

	ctx.ShouldBindJSON(&body)

	climb := models.Climb{
		Date: body.Date,
		Comment: body.Comment,
		Images: body.Images,
		RouteID: body.RouteId,
		UserProfileID: body.UserId,
	}

	fmt.Println(climb)

	result := inits.DB.Create(&climb)
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": climb})
}

func GetClimbs(ctx *gin.Context){
	var climbs []models.Climb

	result := inits.DB.Find(&climbs)
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": climbs})
}

func GetClimb(ctx *gin.Context){
	var climb models.Climb

	result := inits.DB.First(&climb, ctx.Param("id"))
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	ctx.JSON(200, gin.H{"data": climb})
}

func UpdateClimb(ctx *gin.Context){
	var body struct{
		Comment string
		Images string
	}

	ctx.BindJSON(&body)

	var climb models.Climb

	result := inits.DB.First(&climb, ctx.Param("id"))
	if result.Error != nil {
		ctx.JSON(500, gin.H{"error": result.Error})
		return
	}

	inits.DB.Model(&climb).Updates(models.Climb{
		Comment: body.Comment,
		Images: body.Images,
	})

	ctx.JSON(200, gin.H{"data": climb})
}

func DeleteClimb(ctx *gin.Context){
	id := ctx.Param("id")

	inits.DB.Delete(&models.Climb{}, id)

	ctx.JSON(200, gin.H{"data": "Climb has been deleted successfully"})
}

