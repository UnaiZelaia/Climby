CREATE TABLE user_profile (
    id          SERIAL PRIMARY KEY,
    username    varchar(20) NOT NULL,
    email       varchar(30) NOT NULL,
    bio         varchar(1000) NOT NULL,
    password    varchar(100) NOT NULL
);

CREATE TABLE route (
    id          SERIAL PRIMARY KEY,
    type        varchar(40) NOT NULL,
    name        varchar(50) NOT NULL,
    difficulty  varchar(20),
    images      varchar(100),
    description varchar(10000) NOT NULL,
    location    varchar(1000) NOT NULL
);

CREATE TABLE climbs (
    id          SERIAL PRIMARY KEY,
    user_profile       INT NOT NULL,
    route       INT NOT NULL,
    date        DATE NOT NULL,
    comment     varchar(500),
    images      varchar(500),

    CONSTRAINT fk_user_climb
        FOREIGN KEY(user_profile)
            REFERENCES user_profile(id),

    CONSTRAINT fk_route_climb
        FOREIGN KEY(route)
            REFERENCES route(id)
);

CREATE TABLE user_routes (
    user_profile       INT,
    route       INT,
    PRIMARY KEY(user_profile, route),

    CONSTRAINT fk_user_route_user
        FOREIGN KEY(user_profile)
            REFERENCES user_profile(id),
    
    CONSTRAINT fk_user_route_route
        FOREIGN KEY(route)
            REFERENCES route(id)
);

CREATE TABLE user_likes (
    user_profile    INT,
    route           INT,

    PRIMARY KEY(user_profile, route),

    CONSTRAINT fk_user_likes_user
        FOREIGN KEY(user_profile)
            REFERENCES user_profile(id),
    
    CONSTRAINT fk_user_likes_route
        FOREIGN KEY(route)
            REFERENCES route(id)
);

CREATE TABLE route_comments (
    user_profile       INT,
    route       INT,
    comment     varchar(200),

    PRIMARY KEY(user_profile, route),

    CONSTRAINT fk_route_comments_user
        FOREIGN KEY(user_profile)
            REFERENCES user_profile(id),
    
    CONSTRAINT fk_route_comments_route
        FOREIGN KEY(route)
            REFERENCES route(id)
);

CREATE TABLE user_completed_routes (
    user_profile       INT,
    route       INT,

    PRIMARY KEY(user_profile, route),

    CONSTRAINT fk_user_completed_routes_user
        FOREIGN KEY(user_profile)
            REFERENCES user_profile(id),
    
    CONSTRAINT fk_user_completed_routes_route
        FOREIGN KEY(route)
            REFERENCES route(id)
);