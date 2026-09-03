// Frontend/movies.js

const API_URL = "http://localhost:8080/api/movies";

// ================================
// LẤY DANH SÁCH PHIM
// ================================
async function getMovies() {
    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Không thể lấy danh sách phim");
        }

        const movies = await response.json();

        console.log("Danh sách phim:", movies);

        return movies;
    } catch (error) {
        console.error("Lỗi:", error);
        return [];
    }
}


// ================================
// LẤY PHIM THEO ID
// ================================
async function getMovieById(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);

        if (!response.ok) {
            throw new Error("Không tìm thấy phim");
        }

        const movie = await response.json();

        console.log("Phim:", movie);

        return movie;
    } catch (error) {
        console.error("Lỗi:", error);
        return null;
    }
}


// ================================
// THÊM PHIM
// ================================
async function addMovie(movie) {
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(movie)
        });

        const result = await response.text();

        if (!response.ok) {
            throw new Error(result);
        }

        console.log("Thêm phim:", result);

        return result;
    } catch (error) {
        console.error("Lỗi:", error);
        return null;
    }
}


// ================================
// HIỂN THỊ DANH SÁCH PHIM
// ================================
async function displayMovies() {
    const movies = await getMovies();

    const movieList = document.getElementById("movieList");

    if (!movieList) {
        console.log("Không tìm thấy movieList");
        return;
    }

    movieList.innerHTML = "";

    movies.forEach(movie => {
        const div = document.createElement("div");

        div.innerHTML = `
            <h3>${movie.title}</h3>
            <p>ID: ${movie.id}</p>
            <p>Thể loại: ${movie.genre}</p>
            <p>Thời lượng: ${movie.duration} phút</p>
        `;

        movieList.appendChild(div);
    });
}


// ================================
// TEST
// ================================
displayMovies();
