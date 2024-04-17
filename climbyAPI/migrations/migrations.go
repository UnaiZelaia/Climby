package main

import (
	"climbyAPI/inits"
	"climbyAPI/models"
)

func init() {
	inits.LoadEnv()
	inits.DBInit()
}

func main() {
	inits.DB.AutoMigrate(&models.UserProfile{})
	inits.DB.AutoMigrate(&models.Climb{})
	inits.DB.AutoMigrate(&models.Route{})
	inits.DB.AutoMigrate(&models.UserComments{})
}
