package models

import (
	"time"

	"gorm.io/gorm"
)

type Climb struct {
	gorm.Model
	Date 	time.Time
	Comment string
	Images 	string
	RouteID uint
	UserProfileID	uint
}