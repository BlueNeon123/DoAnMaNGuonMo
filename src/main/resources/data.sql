DELETE FROM users;
INSERT INTO users (id, username, full_name, password, role) 
VALUES (1, 'admin', 'Quản Lý', '$2a$10$EblZqNptyYvcLm/VwDCVAuIssGNT2Q1QyNofRzVqKx5V3Q./R2Fq6', 'ADMIN');


-- 1. Xóa dữ liệu cũ (Xóa theo thứ tự để không dính lỗi khóa ngoại)
DELETE FROM order_details;
DELETE FROM orders;
DELETE FROM dishes;
DELETE FROM categories;
DELETE FROM tables;
DELETE FROM users;

-- 2. Chèn 4 cái bàn
INSERT INTO tables (id, name, status) VALUES (1, 'Bàn 1', 'EMPTY');
INSERT INTO tables (id, name, status) VALUES (2, 'Bàn 2', 'EMPTY');
INSERT INTO tables (id, name, status) VALUES (3, 'Bàn 3', 'EMPTY');
INSERT INTO tables (id, name, status) VALUES (4, 'Mang về', 'EMPTY');

-- 3. Chèn 2 danh mục
INSERT INTO categories (id, name) VALUES (1, 'Món chính');
INSERT INTO categories (id, name) VALUES (2, 'Đồ uống');

-- 4. Chèn 4 món ăn
INSERT INTO dishes (id, name, price, category_id) VALUES (1, 'Cơm sườn', 35000, 1);
INSERT INTO dishes (id, name, price, category_id) VALUES (2, 'Phở bò', 40000, 1);
INSERT INTO dishes (id, name, price, category_id) VALUES (3, 'Coca Cola', 15000, 2);
INSERT INTO dishes (id, name, price, category_id) VALUES (4, 'Nước cam', 20000, 2);

-- 5. Chèn 2 user (Mật khẩu đã được mã hóa chuẩn xác)
INSERT INTO users (id, username, full_name, password, role) 
VALUES (1, 'admin', 'Quản Lý', '$2a$10$3gA.L1N..bK561gJm/iLd.E.1s.g.NShO.bA.pG0.jR0Y5.x.R.aC', 'ADMIN');

INSERT INTO users (id, username, full_name, password, role) 
VALUES (2, 'staff', 'Nhân Viên', '$2a$10$3gA.L1N..bK561gJm/iLd.E.1s.g.NShO.bA.pG0.jR0Y5.x.R.aC', 'STAFF');

INSERT INTO users (id, username, full_name, password, role) 
VALUES (3, 'admin2', 'Quản Lý', '123456', 'ADMIN');

INSERT INTO users (id, username, full_name, password, role) 
VALUES (4, 'staff2', 'Nhân Viên', '123456', 'STAFF');