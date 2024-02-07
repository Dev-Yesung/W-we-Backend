document.addEventListener("DOMContentLoaded", function () {
  const newPostBtn = document.getElementById("new-post-btn");
  const newPostModal = new bootstrap.Modal(
    document.getElementById("new-post-modal")
  );

  newPostBtn.addEventListener("click", function () {
    newPostModal.toggle();
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const recommendMusicBtn = document.getElementById("recommend-music");
  const uploadMyMusicBtn = document.getElementById("upload-my-music");
  const modalContainer = document.querySelector(".new-post-modal-container");

  recommendMusicBtn.addEventListener("click", function () {
    modalContainer.innerHTML = `
      <form>
        <div class="mb-3">
          <label for="musicName" class="form-label">음악 제목</label>
          <input type="text" class="form-control" id="musicName">
        </div>
        <div class="mb-3">
          <label for="artistName" class="form-label">아티스트 이름</label>
          <input type="text" class="form-control" id="artistName">
        </div>
        <div class="mb-3">
          <label for="musicLink" id="musicLink-form" class="form-label">음원 링크</label>
          <span id="new-post-link-share-alert" class="btn" data-tooltip="추천하고 싶은 음악의&#10유튜브 링크를 넣어주세요.&#10현재는 유튜브만 지원됩니다."></span>
          <input type="text" class="form-control" id="musicLink" placeholder="https://www.youtube.com/...">
        </div>
        <div class="mb-3">
          <label for="postTitle" class="form-label">글 제목</label>
          <input type="text" class="form-control" id="postTitle">
        </div>
        <div class="mb-3">
          <label for="postContent" class="form-label">글 내용</label>
          <textarea class="form-control" id="postContent" rows="3"></textarea>
        </div>
      </form>
    `;
  });

  uploadMyMusicBtn.addEventListener("click", function () {
    modalContainer.innerHTML = `
      <form>
        <div class="mb-3">
          <label for="musicName" class="form-label">음악 제목</label>
          <input type="text" class="form-control" id="musicName">
        </div>
        <div class="mb-3">
          <label for="artistName" class="form-label">아티스트 이름</label>
          <input type="text" class="form-control" id="artistName">
        </div>
        <div class="mb-3">
          <label for="uploadMusic" class="form-label">음원 업로드</label>
          <span id="new-post-upload-music-alert" class="btn" data-tooltip="본인의 작업물을&#10많은 사람들에게 추천해보세요!&#10 10MB 미만의 음원 포맷만&#10지원됩니다."></span>
          <input type="file" class="form-control" id="uploadMusic">
        </div>
        <div class="mb-3">
          <label for="postTitle" class="form-label">글 제목</label>
          <input type="text" class="form-control" id="postTitle">
        </div>
        <div class="mb-3">
          <label for="postContent" class="form-label">글 내용</label>
          <textarea class="form-control" id="postContent" rows="3"></textarea>
        </div>
      </form>
    `;
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const linkShareAlert = document.getElementById("new-post-link-share-alert");

  linkShareAlert.addEventListener("click", function () {
    const tooltip = document.createElement("div");
    tooltip.classList.add("tooltip");
    tooltip.textContent = linkShareAlert.alt;
    tooltip.style.position = "absolute";
    tooltip.style.top =
      linkShareAlert.offsetTop + linkShareAlert.offsetHeight + "px";
    tooltip.style.left = linkShareAlert.offsetLeft + "px";
    document.body.appendChild(tooltip);

    // 클릭한 이미지 영역 이외의 다른 곳을 클릭하면 말풍선을 제거합니다.
    document.addEventListener("click", function (event) {
      if (event.target !== linkShareAlert && event.target !== tooltip) {
        tooltip.remove();
      }
    });
  });
});
