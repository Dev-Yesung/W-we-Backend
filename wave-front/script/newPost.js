document.addEventListener("DOMContentLoaded", function () {
  const newPostBtn = document.getElementById("new-post-btn");
  const newPostModal = new bootstrap.Modal(
    document.getElementById("new-post-modal")
  );

  newPostBtn.addEventListener("click", function () {
    newPostModal.toggle();
  });
});

const modalContainer = document.querySelector(".new-post-modal-container");
document.addEventListener("DOMContentLoaded", function () {
  const recommendMusicBtn = document.getElementById("recommend-music");
  const uploadMyMusicBtn = document.getElementById("upload-my-music");

  recommendMusicBtn.addEventListener("click", function () {
    modalContainer.innerHTML = `
      <form id="recommendMusicUpload">
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
      <form id="myMusicUpload">
        <div class="mb-3">
          <label for="musicName" class="form-label">음악 제목</label>
          <input type="text" class="form-control" id="musicName">
        </div>
        <div class="mb-3">
          <label for="artistName" class="form-label">아티스트 이름</label>
          <input type="text" class="form-control" id="artistName">
        </div>
        <div class="mb-3">
          <label for="uploadMusic" class="form-label">이미지 업로드</label>
          <span id="" class="btn" data-tooltip="작업물의 이미지를&#10 올려주세요.&#10 5MB 미만의 이미지만&#10지원됩니다."></span>
          <input type="file" class="form-control" id="uploadMusic">
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

const newPostUploadBtn = document.getElementById("newPostUploadBtn");
newPostUploadBtn.addEventListener("click", function (event) {
  event.preventDefault();

  const inputs =
    event.target.parentElement.parentElement.parentElement.children[1]
      .children[0].children[0];

  if (inputs === undefined) {
    alert("먼저 포스트를 작성해주세요.");
    return;
  }

  if (inputs.id === "recommendMusicUpload") {
    const musicName = inputs.children[0].children[1].value;
    const artistName = inputs.children[1].children[1].value;
    const link = inputs.children[2].children[2].value;
    const title = inputs.children[3].children[1].value;
    const description = inputs.children[4].children[1].value;

    uploadRecommentMusic(musicName, artistName, link, title, description);
  } else if (inputs.id === "myMusicUpload") {
    const musicName = inputs.children[0].children[1].value;
    const artistName = inputs.children[1].children[1].value;
    const imageFile = inputs.children[2].children[2].files;
    const musicFile = inputs.children[3].children[2].files;
    const title = inputs.children[4].children[1].value;
    const description = inputs.children[5].children[1].value;

    uploadMyMusic(
      musicName,
      artistName,
      imageFile,
      musicFile,
      title,
      description
    );
  }
});

function uploadRecommentMusic(musicName, artistName, link, title, description) {
  const convertLink = convertLinkFormat(link);
  if (convertLink === null) {
    return;
  }
  const data = {
    artistName: artistName,
    songName: musicName,
    title: title,
    descriptions: description,
    musicUrl: convertLink,
  };

  fetch("http://localhost:8080/api/posts/shared-music", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => {
      if (!response.ok) {
        alert("포스트를 업로드 할 수 없습니다.");
        throw new Error("포스트를 업로드 할 수 없습니다.");
      }

      return response.json();
    })
    .then((data) => {
      alert("포스트 업로드가 완료되었습니다.");
      location.reload();
    })
    .catch((error) => {
      console.error("포스트 중에 오류가 발생했습니다.", error);
    });
}

function uploadMyMusic(
  musicName,
  artistName,
  imageFile,
  musicFile,
  title,
  description
) {
  const data = {
    artistName: artistName,
    songName: musicName,
    title: title,
    descriptions: description,
  };
  const imageFileInput = imageFile[0];
  const musicFileInput = musicFile[0];

  const uploadRequest = new FormData();
  uploadRequest.append(
    "request",
    new Blob([JSON.stringify(data)], {
      type: "application/json",
    })
  );
  uploadRequest.append("imageFile", imageFileInput);
  uploadRequest.append("musicFile", musicFileInput);

  fetch("http://localhost:8080/api/posts/my-music", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: uploadRequest,
  })
    .then((response) => {
      if (!response.ok) {
        alert("포스트를 업로드 할 수 없습니다.");
        throw new Error("포스트를 업로드 할 수 없습니다.");
      }

      return response.json();
    })
    .then((data) => {
      alert("포스트 업로드가 완료되었습니다.");
      location.reload();
    })
    .catch((error) => {
      console.error("포스트 중에 오류가 발생했습니다.", error);
    });
}

function convertLinkFormat(link) {
  if (link.startsWith("https://www.youtube.com/watch?v=")) {
    const id = link.split("v=")[1];
    return `https://www.youtube.com/embed/${id}`;
  } else {
    alert("추천하려는 음악의 링크는 유튜브만 가능합니다.");
    return null;
  }
}
