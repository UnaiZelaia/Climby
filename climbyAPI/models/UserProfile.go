package models

import "gorm.io/gorm"

type UserProfile struct {
	gorm.Model
	Username  		string `gorm:"unique"`
	Email   		string `gorm:"unique"`
	Bio  			string
	Password  		string
	Routes 			[]Route
	RouteLikes		[]Route `gorm:"many2many:user_likes;"`
	RouteComments	[]Route `gorm:"many2many:user_comments;"`
	RoutesCompleted	[]Route `gorm:"many2many:user_completed_routes;"`
	Climbs 			[]Climb `gorm:"many2many:user_climbs;"`
}
