// ========================================
// CUSTOMER.JS
// Quản lý khách hàng
// ========================================

// File dữ liệu khách hàng
const CUSTOMER_DATA_URL = "../src/data/customers.json";

// Danh sách khách hàng
let customers = [];

// ========================================
// ĐỌC DỮ LIỆU KHÁCH HÀNG
// ========================================

async function loadCustomers() {
    try {
        const response = await fetch(CUSTOMER_DATA_URL);

        if (!response.ok) {
            throw new Error("Không thể đọc customers.json");
        }

        customers = await response.json();

        console.log("Đã tải khách hàng:", customers);

        return customers;

    } catch (error) {
        console.error("Lỗi khi tải khách hàng:", error);
        customers = [];
        return [];
    }
}


// ========================================
// TÌM KHÁCH HÀNG THEO ID
// ========================================

function findCustomerById(id) {
    if (!id) {
        return null;
    }

    return customers.find(
        customer => customer.id.toLowerCase() === id.toLowerCase()
    );
}


// ========================================
// TÌM KHÁCH HÀNG THEO EMAIL
// ========================================

function findCustomerByEmail(email) {
    if (!email) {
        return null;
    }

    return customers.find(
        customer =>
            customer.email.toLowerCase() === email.toLowerCase()
    );
}


// ========================================
// TÌM KHÁCH HÀNG THEO SỐ ĐIỆN THOẠI
// ========================================

function findCustomerByPhone(phone) {
    if (!phone) {
        return null;
    }

    return customers.find(
        customer => customer.phone === phone
    );
}


// ========================================
// KIỂM TRA EMAIL
// ========================================

function isValidEmail(email) {
    if (!email) {
        return false;
    }

    const emailRegex =
        /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;

    return emailRegex.test(email);
}


// ========================================
// KIỂM TRA SỐ ĐIỆN THOẠI
// ========================================

function isValidPhone(phone) {
    if (!phone) {
        return false;
    }

    const phoneRegex = /^0[35789][0-9]{8}$/;

    return phoneRegex.test(phone);
}


// ========================================
// THÊM KHÁCH HÀNG
// ========================================

function addCustomer(customer) {

    if (!customer) {
        console.error("Khách hàng không được để trống!");
        return false;
    }

    if (!customer.id) {
        console.error("ID khách hàng không được để trống!");
        return false;
    }

    if (!customer.fullName) {
        console.error("Họ tên không được để trống!");
        return false;
    }

    if (!isValidPhone(customer.phone)) {
        console.error("Số điện thoại không hợp lệ!");
        return false;
    }

    if (!isValidEmail(customer.email)) {
        console.error("Email không hợp lệ!");
        return false;
    }

    // Kiểm tra ID đã tồn tại
    if (findCustomerById(customer.id)) {
        console.error("ID khách hàng đã tồn tại!");
        return false;
    }

    // Kiểm tra email đã tồn tại
    if (findCustomerByEmail(customer.email)) {
        console.error("Email đã tồn tại!");
        return false;
    }

    // Kiểm tra số điện thoại
    if (findCustomerByPhone(customer.phone)) {
        console.error("Số điện thoại đã tồn tại!");
        return false;
    }

    customers.push(customer);

    console.log("Thêm khách hàng thành công!");
    return true;
}


// ========================================
// SỬA KHÁCH HÀNG
// ========================================

function updateCustomer(id, newData) {

    const customer = findCustomerById(id);

    if (!customer) {
        console.error("Không tìm thấy khách hàng!");
        return false;
    }

    if (newData.email &&
        !isValidEmail(newData.email)) {

        console.error("Email không hợp lệ!");
        return false;
    }

    if (newData.phone &&
        !isValidPhone(newData.phone)) {

        console.error("Số điện thoại không hợp lệ!");
        return false;
    }

    if (newData.fullName) {
        customer.fullName = newData.fullName;
    }

    if (newData.email) {
        customer.email = newData.email;
    }

    if (newData.phone) {
        customer.phone = newData.phone;
    }

    if (newData.customerType) {
        customer.customerType = newData.customerType;
    }

    console.log("Cập nhật khách hàng thành công!");

    return true;
}


// ========================================
// XÓA KHÁCH HÀNG
// ========================================

function deleteCustomer(id) {

    const index = customers.findIndex(
        customer =>
            customer.id.toLowerCase() === id.toLowerCase()
    );

    if (index === -1) {
        console.error("Không tìm thấy khách hàng!");
        return false;
    }

    customers.splice(index, 1);

    console.log("Xóa khách hàng thành công!");

    return true;
}


// ========================================
// HIỂN THỊ DANH SÁCH KHÁCH HÀNG
// ========================================

function displayCustomers() {

    if (customers.length === 0) {
        console.log("Danh sách khách hàng đang trống!");
        return;
    }

    console.table(customers);
}


// ========================================
// TÍNH GIẢM GIÁ THEO LOẠI KHÁCH HÀNG
// ========================================

function getCustomerDiscount(customerType) {

    const discount = {
        NORMAL: 0,
        STUDENT: 0.10,
        VIP: 0.20
    };

    return discount[customerType] || 0;
}


// ========================================
// TÍNH GIÁ VÉ SAU KHI GIẢM
// ========================================

function calculateCustomerPrice(price, customerType) {

    const discount =
        getCustomerDiscount(customerType);

    return price * (1 - discount);
}


// ========================================
// TÌM KHÁCH HÀNG ĐANG ĐĂNG NHẬP
// ========================================

function getCurrentCustomer() {

    const customerId =
        localStorage.getItem("customerId");

    if (!customerId) {
        return null;
    }

    return findCustomerById(customerId);
}


// ========================================
// LƯU KHÁCH HÀNG ĐANG ĐĂNG NHẬP
// ========================================

function setCurrentCustomer(customerId) {

    const customer =
        findCustomerById(customerId);

    if (!customer) {
        console.error("Không tìm thấy khách hàng!");
        return false;
    }

    localStorage.setItem(
        "customerId",
        customer.id
    );

    console.log(
        "Đã đăng nhập với tài khoản:",
        customer.fullName
    );

    return true;
}


// ========================================
// ĐĂNG XUẤT
// ========================================

function logoutCustomer() {

    localStorage.removeItem("customerId");

    console.log("Đã đăng xuất!");
}


// ========================================
// TỰ ĐỘNG TẢI DỮ LIỆU
// ========================================

document.addEventListener("DOMContentLoaded", async () => {

    await loadCustomers();

    console.log(
        "Customer.js đã sẵn sàng!"
    );

});