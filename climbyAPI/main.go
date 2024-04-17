package main

import (
	"climbyAPI/controllers"
	"climbyAPI/inits"
	"climbyAPI/middlewares"

	"github.com/gin-gonic/gin"
)

func init() {

	inits.LoadEnv()
	inits.DBInit()
}

func main() {
	r := gin.Default()

	// POST endpoints
	r.POST("/api/v1/user/signup", controllers.Signup)
	r.POST("/api/v1/user/login", controllers.Login)
	r.POST("/api/v1/route/new", middlewares.RequireAuth, controllers.CreateRoute)
	r.POST("/api/v1/route/:id/comment", middlewares.RequireAuth, controllers.CreateComment)
	r.POST("/api/v1/climb/new", middlewares.RequireAuth, controllers.CreateClimb)

	// GET 1 endpoints
	r.GET("/api/v1/user/logout", middlewares.RequireAuth, controllers.Logout)
	r.GET("/api/v1/user/:id", middlewares.RequireAuth, controllers.GetUser)
	r.GET("/api/v1/route/:id", middlewares.RequireAuth, controllers.GetRoute)
	r.GET("/api/v1/comment/:id", middlewares.RequireAuth, controllers.GetComment)
	r.GET("/api/v1/climb/:id", middlewares.RequireAuth, controllers.GetClimb)

	// GET all endpoints
	r.GET("/api/v1/users", middlewares.RequireAuth, controllers.GetUsers)
	r.GET("/api/v1/routes", middlewares.RequireAuth, controllers.GetRoutes)
	r.GET("/api/v1/comments", middlewares.RequireAuth, controllers.GetComments)
	r.GET("/api/v1/climbs", middlewares.RequireAuth, controllers.GetClimbs)

	// PUT endpoints
	r.PUT("/api/v1/user/:id", middlewares.RequireAuth, controllers.UpdateUser)
	r.PUT("/api/v1/route/:id", middlewares.RequireAuth, controllers.UpdateRoute)
	r.PUT("/api/v1/comment/:id", middlewares.RequireAuth, controllers.UpdateComment)
	r.PUT("/api/v1/climb/:id", middlewares.RequireAuth, controllers.UpdateComment)

	//DELETE endpoint
	r.DELETE("/api/v1/user/:id", middlewares.RequireAuth, controllers.DeleteUser)
	r.DELETE("/api/v1/route/:id", middlewares.RequireAuth, controllers.DeleteRoute)
	r.DELETE("/api/v1/comment/:id", middlewares.RequireAuth, controllers.DeleteComment)
	r.DELETE("/api/v1/cimb/:id", middlewares.RequireAuth, controllers.DeleteClimb)

	r.Run()
}
