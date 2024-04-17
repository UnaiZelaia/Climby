package models

import "gorm.io/gorm"

type Route struct {
	gorm.Model
	Type        	string
	Name        	string
	Difficulty  	string
	Images      	string
	Description 	string
	Location    	string
	Climbs      	[]Climb
	UserProfileID	uint `gorm:"column:user_id"`
}
