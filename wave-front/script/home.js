const postContainer = document.querySelector("#post-container");

const goToTopBtn = document.createElement("button");
goToTopBtn.id = "goToTopBtn";
goToTopBtn.style.display = "none";
goToTopBtn.innerHTML = "<img src='/image/goToTop-btn.svg' />";
goToTopBtn.onclick = function () {
  window.scrollTo({
    top: 0,
    behavior: "smooth",
  });
};
document.body.appendChild(goToTopBtn);
window.addEventListener("scroll", function () {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    goToTopBtn.style.display = "block";
  } else {
    goToTopBtn.style.display = "none";
  }
});

window.addEventListener("scroll", function () {
  const header = document.querySelector("header");
  const scrollPosition = window.scrollY;

  // 현재 스크롤 위치가 헤더의 아래에 있을 때
  if (scrollPosition > header.offsetHeight) {
    header.classList.add("header-fixed"); // header에 새로운 클래스 추가
  } else {
    header.classList.remove("header-fixed"); // header 클래스 제거
  }
});
