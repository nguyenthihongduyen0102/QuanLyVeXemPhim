const giaGoc = 80000;
let dsGhe = [];
let gheDangChon = [];
const container = document.getElementById("layoutGhe");
const txtList = document.getElementById("listGhe");
const txtTien = document.getElementById("txtTongTien");
function layDanhSachGheTuBackend() {
    const urlApiJava = "http://localhost:8080/api/seats?roomId=Room01";
    fetch(urlApiJava)
        .then(response => {
            if (!response.ok) {
                throw new Error("Lỗi kết nối đến máy chủ Java Backend!");
            }
            return response.json();
        })
        .then(data => {
            dsGhe = data;
            veSoDo();
        })
        .catch(error => {
            console.error("Hệ thống Frontend gặp lỗi: ", error);
            alert("Không thể tải sơ đồ ghế từ máy chủ Java!");
        });
}
function veSoDo() {
    if (!container) return;
    container.innerHTML = "";
    dsGhe.forEach(ghe => {
        const nodeGhe = document.createElement("div");
        nodeGhe.classList.add("seat");
        nodeGhe.innerText = ghe.id || ghe.seatCode;
        let loaiGhe = ghe.loai || ghe.typeOfSeat;
        if (loaiGhe === "VIPChair") nodeGhe.classList.add("vip");
        if (loaiGhe === "Cuplechair") nodeGhe.classList.add("double");
        let trangThaiGhe = ghe.trangThai || ghe.seatStatus;
        if (trangThaiGhe === "Booked") {
            nodeGhe.classList.add("booked");
        } else {
            nodeGhe.addEventListener("click", () => clickGhe(ghe, nodeGhe));
        }
        container.appendChild(nodeGhe);
    });
}
layDanhSachGheTuBackend();
