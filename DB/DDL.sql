CREATE TABLE users (
    user_id INT PRIMARY KEY IDENTITY(1,1), 
    user_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(10) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    cover_image VARCHAR(255),
    biography VARCHAR(500) NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE posts (
    post_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT,
    content VARCHAR(500) NOT NULL,
    image VARCHAR(255),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE comment (
    comment_id INT PRIMARY KEY IDENTITY(1,1), 
    user_id INT,
    post_id INT,
    content VARCHAR(500) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (post_id) REFERENCES Posts(post_id)
);

