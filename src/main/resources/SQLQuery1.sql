-- Tạo cơ sở dữ liệu
CREATE DATABASE ShopQuanAo;
go
USE ShopQuanAo;
go
-- Bảng người dùng
CREATE TABLE users (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       username NVARCHAR(50) UNIQUE NOT NULL,
                       email NVARCHAR(100) UNIQUE NOT NULL,
                       password NVARCHAR(255) NOT NULL,
                       full_name NVARCHAR(100),
                       phone NVARCHAR(15),
                       address NVARCHAR(255),
                       role NVARCHAR(20) NOT NULL CHECK (role IN ('USER', 'ADMIN')),
                       image_url NVARCHAR(255) -- Ảnh đại diện
);

-- Bảng danh mục sản phẩm
CREATE TABLE categories (
                            id INT IDENTITY(1,1) PRIMARY KEY,
                            name NVARCHAR(100) UNIQUE NOT NULL,
                            description NVARCHAR(255)
);

-- Bảng màu sắc
CREATE TABLE colors (
                        id INT IDENTITY(1,1) PRIMARY KEY,
                        name NVARCHAR(50) UNIQUE NOT NULL
);

-- Bảng kích cỡ
CREATE TABLE sizes (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       name NVARCHAR(50) UNIQUE NOT NULL
);

-- Bảng thương hiệu
CREATE TABLE brands (
                        id INT IDENTITY(1,1) PRIMARY KEY,
                        name NVARCHAR(100) UNIQUE NOT NULL
);

-- Bảng sản phẩm
CREATE TABLE products (
                          id INT IDENTITY(1,1) PRIMARY KEY,
                          name NVARCHAR(255) NOT NULL,
                          description NVARCHAR(MAX),
                          price DECIMAL(10,2) NOT NULL,
                          category_id INT NOT NULL,
                          image_url NVARCHAR(255), -- Ảnh chính của sản phẩm
                          FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- Bảng chi tiết sản phẩm
CREATE TABLE product_detail (
                                id INT IDENTITY(1,1) PRIMARY KEY,
                                product_id INT NOT NULL,
                                color_id INT,
                                size_id INT,
                                brand_id INT,
                                stock INT NOT NULL CHECK (stock >= 0), -- Số lượng tồn kho
                                image_url NVARCHAR(255), -- Ảnh cho từng biến thể sản phẩm
                                FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                FOREIGN KEY (color_id) REFERENCES colors(id) ON DELETE SET NULL,
                                FOREIGN KEY (size_id) REFERENCES sizes(id) ON DELETE SET NULL,
                                FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE SET NULL
);

-- Bảng đơn hàng
CREATE TABLE orders (
                        id INT IDENTITY(1,1) PRIMARY KEY,
                        user_id INT NOT NULL,
                        total_price DECIMAL(10,2) NOT NULL CHECK (total_price >= 0),
                        status NVARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'SHIPPED', 'CANCELED', 'COMPLETED')),
                        created_at DATETIME DEFAULT GETDATE(),
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Bảng chi tiết đơn hàng
CREATE TABLE order_items (
                             id INT IDENTITY(1,1) PRIMARY KEY,
                             order_id INT NOT NULL,
                             product_detail_id INT NOT NULL,
                             quantity INT NOT NULL CHECK (quantity > 0),
                             price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
                             FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                             FOREIGN KEY (product_detail_id) REFERENCES product_detail(id) ON DELETE CASCADE
);

-- Bảng giỏ hàng
CREATE TABLE cart_items (
                            id INT IDENTITY(1,1) PRIMARY KEY,
                            user_id INT NOT NULL,
                            product_id INT NOT NULL,
                            quantity INT NOT NULL CHECK (quantity > 0),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Bảng thanh toán
CREATE TABLE payments (
                          id INT IDENTITY(1,1) PRIMARY KEY,
                          order_id INT NOT NULL,
                          payment_method NVARCHAR(50) NOT NULL CHECK (payment_method IN ('CASH', 'CREDIT_CARD', 'PAYPAL')),
                          status NVARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED')),
                          transaction_id NVARCHAR(100) UNIQUE,
                          FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Bảng users
INSERT INTO users (username, email, password, full_name, phone, address, role, image_url)
VALUES 
    (N'admin', N'admin@fashion.com', N'password123', N'Nguyễn Bá Đoàn', N'0123456789', N'Hà Nội', N'ADMIN', N'admin.png'),
    (N'user1', N'user1@fashion.com', N'pass123', N'Trần Văn A', N'0987654321', N'TP HCM', N'USER', N'user1.png'),
    (N'user2', N'user2@fashion.com', N'pass456', N'Lê Thị B', N'0123456788', N'Đà Nẵng', N'USER', N'user2.png'),
    (N'user3', N'user3@fashion.com', N'pass789', N'Phạm Công C', N'0999999999', N'Huế', N'USER', N'user3.png'),
    (N'user4', N'user4@fashion.com', N'pass000', N'Hoàng Văn D', N'0911111111', N'Hải Phòng', N'USER', N'user4.png');

-- Bảng categories
INSERT INTO categories (name, description)
VALUES 
    (N'Áo', N'Các loại áo thun, sơ mi, hoodie...'),
    (N'Quần', N'Quần jeans, kaki, short...'),
    (N'Váy', N'Váy công sở, dạo phố, dự tiệc...'),
    (N'Giày dép', N'Giày thể thao, sandal, boot...'),
    (N'Phụ kiện', N'Nón, túi xách, dây chuyền...');

-- Bảng colors
INSERT INTO colors (name)
VALUES 
    (N'Đỏ'),
    (N'Xanh'),
    (N'Đen'),
    (N'Trắng'),
    (N'Be');

-- Bảng sizes
INSERT INTO sizes (name)
VALUES 
    (N'S'),
    (N'M'),
    (N'L'),
    (N'XL'),
    (N'XXL');

-- Bảng brands
INSERT INTO brands (name)
VALUES 
    (N'Zara'),
    (N'Gucci'),
    (N'Adidas'),
    (N'Nike'),
    (N'H&M');

-- Bảng products
INSERT INTO products (name, description, price, category_id, image_url)
VALUES 
    (N'Áo thun basic', N'Áo thun cổ tròn chất liệu cotton', 300000, 1, N'aothun.png'),
    (N'Quần jeans nam', N'Quần jeans nam ống đứng', 600000, 2, N'quanjeans.png'),
    (N'Váy maxi nữ', N'Váy maxi phù hợp cho đi biển', 500000, 3, N'vaymaxi.png'),
    (N'Giày sneaker', N'Giày thể thao năng động', 800000, 4, N'sneaker.png'),
    (N'Nón bucket', N'Nón bucket chống nắng', 250000, 5, N'nonbucket.png');

-- Bảng product_detail
-- Bảng product_detail: Mỗi sản phẩm có 2 màu và 2 size
INSERT INTO product_detail (product_id, color_id, size_id, brand_id, stock, image_url)
VALUES 
    -- Sản phẩm 1: Áo thun basic
    (1, 1, 1, 5, 50, N'aothun.png'), -- Đỏ, size S
    (1, 1, 2, 5, 50, N'aothun.png'), -- Đỏ, size M
    (1, 2, 1, 5, 50, N'aothun.png'), -- Xanh, size S
    (1, 2, 2, 5, 50, N'aothun.png'), -- Xanh, size M
    
    -- Sản phẩm 2: Quần jeans nam
    (2, 3, 3, 1, 30, N'jeans.png'), -- Đen, size L
    (2, 3, 4, 1, 30, N'jeans.png'), -- Đen, size XL
    (2, 4, 3, 1, 30, N'jeans.png'), -- Trắng, size L
    (2, 4, 4, 1, 30, N'jeans.png'), -- Trắng, size XL
    
    -- Sản phẩm 3: Váy maxi nữ
    (3, 1, 1, 2, 40, N'vay.png'), -- Đỏ, size S
    (3, 1, 2, 2, 40, N'vay.png'), -- Đỏ, size M
    (3, 2, 1, 2, 40, N'vay.png'), -- Xanh, size S
    (3, 2, 2, 2, 40, N'vay.png'), -- Xanh, size M
    
    -- Sản phẩm 4: Giày sneaker
    (4, 3, 3, 3, 60, N'sneaker.png'), -- Đen, size L
    (4, 3, 4, 3, 60, N'sneaker.png'), -- Đen, size XL
    (4, 2, 3, 3, 60, N'sneaker.png'), -- Xanh, size L
    (4, 2, 4, 3, 60, N'sneaker.png'), -- Xanh, size XL
    
    -- Sản phẩm 5: Nón bucket
    (5, 4, 1, 4, 70, N'non.png'), -- Trắng, size S
    (5, 4, 2, 4, 70, N'non.png'), -- Trắng, size M
    (5, 5, 1, 4, 70, N'non.png'), -- Be, size S
    (5, 5, 2, 4, 70, N'non.png'); -- Be, size M


-- Bảng orders
INSERT INTO orders (user_id, total_price, status)
VALUES 
    (1, 300000, N'PENDING'),
    (2, 600000, N'SHIPPED'),
    (3, 500000, N'COMPLETED'),
    (4, 800000, N'CANCELED'),
    (5, 250000, N'PENDING');

-- Bảng order_items
INSERT INTO order_items (order_id, product_detail_id, quantity, price)
VALUES 
    (1, 1, 1, 300000),
    (2, 2, 1, 600000),
    (3, 3, 1, 500000),
    (4, 4, 1, 800000),
    (5, 5, 1, 250000);

-- Bảng cart_items
INSERT INTO cart_items (user_id, product_id, quantity)
VALUES 
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 1),
    (4, 4, 1),
    (5, 5, 1);

-- Bảng payments
INSERT INTO payments (order_id, payment_method, status, transaction_id)
VALUES 
    (1, N'CASH', N'PENDING', N'TX123456'),
    (2, N'CREDIT_CARD', N'SUCCESS', N'TX123457'),
    (3, N'PAYPAL', N'SUCCESS', N'TX123458'),
    (4, N'CASH', N'FAILED', N'TX123459'),
    (5, N'CREDIT_CARD', N'PENDING', N'TX123460');


select * from product_detail
select * from products

ALTER TABLE orders
DROP CONSTRAINT DF__orders__created___534D60F1;
