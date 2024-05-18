package models

import "gorm.io/gorm"

type UserProfile struct {
	gorm.Model
	Username  		string `gorm:"unique"`
	Email   		string `gorm:"unique"`
	Bio  			string
	Password  		string
	Routes 			[]Route
	RouteLikes		[]Route `gorm:"many2many:user_liked_routes;"`
	RouteComments	[]Route 
	RoutesCompleted	[]Route 
	Climbs 			[]Climb 
}
