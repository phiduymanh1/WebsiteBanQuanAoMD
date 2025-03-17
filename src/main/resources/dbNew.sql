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
                             product_id INT NOT NULL,
                             quantity INT NOT NULL CHECK (quantity > 0),
                             price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
                             FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                             FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
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

-- Thêm người dùng
INSERT INTO users (username, email, password, full_name, phone, address, role, image_url) VALUES
                                                                                              ('admin', 'admin@example.com', 'hashed_password', 'Admin User', '0987654321', 'Hà Nội', 'ADMIN', 'avatar_admin.jpg'),
                                                                                              ('user1', 'user1@example.com', 'hashed_password', 'Nguyễn Văn A', '0912345678', 'TP.HCM', 'USER', 'avatar_user1.jpg'),
                                                                                              ('user2', 'user2@example.com', 'hashed_password', 'Trần Thị B', '0923456789', 'Đà Nẵng', 'USER', 'avatar_user2.jpg');

-- Thêm danh mục sản phẩm
INSERT INTO categories (name, description) VALUES
                                               ('Áo', 'Các loại áo phông, áo khoác, áo sơ mi'),
                                               ('Quần', 'Các loại quần jeans, quần short, quần âu'),
                                               ('Giày dép', 'Các loại giày thể thao, giày công sở');

-- Thêm màu sắc
INSERT INTO colors (name) VALUES
                              ('Đen'), ('Trắng'), ('Xanh dương'), ('Xanh lá'), ('Đỏ');

-- Thêm kích cỡ
INSERT INTO sizes (name) VALUES
                             ('S'), ('M'), ('L'), ('XL');

-- Thêm thương hiệu
INSERT INTO brands (name) VALUES
                              ('Nike'), ('Adidas'), ('Puma'), ('Uniqlo');

-- Thêm sản phẩm
INSERT INTO products (name, description, price, category_id, image_url) VALUES
                                                                            ('Áo Phông Trắng', 'Áo phông màu trắng, chất liệu cotton', 200000, 1, 'ao_phong_trang.jpg'),
                                                                            ('Áo Khoác Jeans', 'Áo khoác jeans nam', 500000, 1, 'ao_khoac_jeans.jpg'),
                                                                            ('Quần Jeans Nam', 'Quần jeans nam, màu xanh', 400000, 2, 'quan_jeans_nam.jpg'),
                                                                            ('Giày Thể Thao Nike', 'Giày thể thao Nike, size 42', 1200000, 3, 'giay_the_thao_nike.jpg');

-- Thêm chi tiết sản phẩm
INSERT INTO product_detail (product_id, color_id, size_id, brand_id, stock, image_url) VALUES
                                                                                           (1, 2, 2, 1, 50, 'ao_phong_trang_m.jpg'),  -- Áo Phông Trắng: Trắng, M, Nike
                                                                                           (2, 1, 3, 2, 30, 'ao_khoac_jeans_l.jpg'),  -- Áo Khoác Jeans: Đen, L, Adidas
                                                                                           (3, 3, 2, 3, 40, 'quan_jeans_nam_m.jpg'),  -- Quần Jeans Nam: Xanh dương, M, Puma
                                                                                           (4, 1, 3, 1, 25, 'giay_the_thao_nike_l.jpg');  -- Giày Thể Thao Nike: Đen, L, Nike

-- Thêm giỏ hàng
INSERT INTO cart_items (user_id, product_id, quantity) VALUES
                                                           (2, 1, 2),  -- Người dùng 2 chọn 2 áo phông trắng
                                                           (2, 3, 1),  -- Người dùng 2 chọn 1 quần jeans nam
                                                           (3, 2, 1);  -- Người dùng 3 chọn 1 áo khoác jeans

-- Thêm đơn hàng
INSERT INTO orders (user_id, total_price, status, created_at) VALUES
                                                                  (1, 700000, 'PENDING', GETDATE()),
                                                                  (2, 1000000, 'SHIPPED', GETDATE()),
                                                                  (3, 500000, 'COMPLETED', GETDATE());

-- Thêm chi tiết đơn hàng
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
                                                                    (1, 1, 2, 200000),  -- Đơn hàng 1: 2 áo phông trắng
                                                                    (1, 2, 1, 500000),  -- Đơn hàng 1: 1 áo khoác jeans
                                                                    (2, 3, 1, 400000);  -- Đơn hàng 2: 1 quần jeans nam

-- Thêm thanh toán
INSERT INTO payments (order_id, payment_method, status, transaction_id) VALUES
                                                                            (1, 'CREDIT_CARD', 'SUCCESS', 'TXN123456789'),
                                                                            (2, 'CASH', 'SUCCESS', 'TXN987654321');

ALTER TABLE orders
DROP CONSTRAINT DF__orders__created___534D60F1;