package models

import (
	"gorm.io/gorm"
)

type UserComments struct {
	gorm.Model
	Comment 		string
	UserProfileID	uint `gorm:"primaryKey;column:user_id"`
	RouteID			uint `gorm:"primaryKey"`
}
