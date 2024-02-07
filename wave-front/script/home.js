// // 게시물 데이터 불러온 후 동적 생성
const postContainer = document.querySelector("#post-container");
// fetch("http://localhost:8080/api/posts", {
//   method: "GET",
//   headers: {
//     Authorization: "Bearer ",
//     "Content-Type": "application/json",
//   },
// })
//   .then((response) => response.json())
//   .then((data) => {
//     data.forEach((post) => {
//       const postElement = document.createElement("div");
//       postElement.className = "post";

//       // 게시물 헤더 생성
//       const postHeader = document.createElement("div");
//       postHeader.className = "post-header";
//       postHeader.innerHTML = `
//                 <img src="${post.profileImage}" alt="프로필 이미지" /> ${post.author} ﹒ ${post.timestamp}
//                 <div>${post.title}</div>
//             `;
//       postElement.appendChild(postHeader);

//       // 게시물 미디어 컨텐츠 생성
//       const postMedia = document.createElement("div");
//       postMedia.className = "post-media";
//       postMedia.innerHTML = `<img src="${post.media}" alt="게시물 미디어 컨텐츠" />`;
//       postElement.appendChild(postMedia);

//       // 게시물 버튼 생성
//       const postButtons = document.createElement("div");
//       postButtons.className = "post-buttons";
//       postButtons.innerHTML = `
//                 <img src="/image/like-btn-icon.svg" class="btn mx-4" />
//                 <button>게시물 모달창 보기</button>
//                 <button>게시물 공유</button>
//                 <button>게시물 스크랩</button>
//             `;
//       postElement.appendChild(postButtons);

//       // 게시물 좋아요 개수 생성
//       const postLikes = document.createElement("div");
//       postLikes.className = "post-likes";
//       postLikes.textContent = `게시물 좋아요 개수: ${post.likes}`;
//       postElement.appendChild(postLikes);

//       // 게시물 본문 생성
//       const postDescriptions = document.createElement("div");
//       postDescriptions.className = "post-descriptions";
//       postDescriptions.textContent = post.description;
//       postElement.appendChild(postDescriptions);

//       // 게시물 댓글 수 및 입력란 생성
//       const postComments = document.createElement("div");
//       postComments.className = "post-comments";
//       postComments.innerHTML = `
//                 <div>댓글 개수 모두 보기</div>
//                 <div>
//                     <input type="text" placeholder="댓글 달기" />
//                     <button>게시버튼</button>
//                 </div>
//             `;
//       postElement.appendChild(postComments);

//       // 생성된 게시물을 컨테이너에 추가
//       postContainer.appendChild(postElement);
//     });
//   })
//   .catch((error) => {
//     console.error("데이터를 가져오는 중 에러 발생: ", error);
//   });

// 테스트 용 더미 데이터
const dummyData = [
  {
    profileImage: "/image/dummy-user-profile.svg",
    author: "@ysng_is_yosong",
    timestamp: "2022-02-01",
    title: "침착맨~~~침착맨~~~침착맨~~~침착맨~~~침착맨~~~",
    media: "/image/dummy-post-img.png",
    likes: 10,
    description:
      "더미 게시물 본문입니다. 저쩌구더미 게시물 본문입니다.더미 게시물 본문입니다. 저쩌구더미 게시물 본문입니다.더미 게시물 본문입니다. 저쩌구더미 게시물 본문입니다.더미 게시물 본문입니다.",
  },
  {
    profileImage: "/image/dummy-user-profile.svg",
    author: "@yesyesyes",
    timestamp: "2022-02-02",
    title: "침착맨~~~침착맨~~~침착맨~~~침착맨~~~침착맨~~~",
    media: "/image/dummy-post-img.png",
    likes: 15,
    description: "더미 게시물 본문입니다. 어쩌구 저쩌구",
  },
];

const n = 20;
if (n === 0) {
  const noPostMessage = document.createElement("div");
  noPostMessage.id = "no-post-message";
  noPostMessage.innerHTML =
    "추천 음악이 없네요 :(<br>당신의 소리를 퍼트려주세요!";
  postContainer.appendChild(noPostMessage);

  const nextPost = document.createElement("div");
  nextPost.id = "more-post-btn";
  nextPost.innerHTML =
    '<img src="/image/more-post-btn.svg" id="more-post-icon" class="btn mr-2" />';
  nextPost.onclick = function () {
    // 추가 작업 수행
  };
  postContainer.appendChild(nextPost);
}

for (let i = 0; i < n; i++) {
  const post = dummyData[i % dummyData.length]; // 더미 데이터 순환

  const postElement = document.createElement("div");
  postElement.id = "post-outter";
  postElement.className = "post card"; // 부트스트랩 card 클래스 추가

  // 게시물 내부 div 생성
  const postInner = document.createElement("div");
  postInner.id = "post-inner";

  // 게시물 헤더 생성
  const postHeader = document.createElement("div");
  postHeader.className = "post-header card-header"; // 부트스트랩 card-header 클래스 추가
  postHeader.innerHTML = `
        <img src="${post.profileImage}" alt="프로필 이미지" class="mr-2 post-profile-img" />
        <div id="post-author">${post.author}</div> 
        <div id="post-time">${post.timestamp}</div>
        <div id="post-title" class="font-weight-bold">${post.title}</div>
    `;

  // 게시물 미디어 컨텐츠 생성
  const postMedia = document.createElement("div");
  postMedia.className = "post-media card-body"; // 부트스트랩 card-body 클래스 추가
  postMedia.innerHTML = `<img src="${post.media}" alt="게시물 미디어 컨텐츠" class="img-fluid" />`; // 부트스트랩 img-fluid 클래스 추가

  // 게시물 버튼 생성
  const postButtons = document.createElement("div");
  postButtons.className = "card-footer card-frame"; // 부트스트랩 card-footer 클래스 추가
  postButtons.innerHTML = `
        <div class="post-btn-group">
          <img src="/image/like-btn-icon.svg" class="btn mr-2 post-left-btn" />
          <img src="/image/more-btn-icon.svg" class="btn mr-2 post-left-btn" />
          <img src="/image/share-btn-icon.svg" class="btn mr-2 post-left-btn" />
          <img src="/image/save-btn-icon.svg" id="save-post-btn" class="btn mr-2 post-right-btn" />
        </div>
    `;

  // 게시물 좋아요 개수 생성
  const postLikes = document.createElement("div");
  postLikes.className = "post-likes card-footer"; // 부트스트랩 card-footer 클래스 추가
  postLikes.innerHTML = `<span id="like-font">좋아요: <span id="like-size">${post.likes}</span>개</span>`;

  // 게시물 본문 생성
  const postDescriptions = document.createElement("div");
  postDescriptions.className = "post-descriptions card-body"; // 부트스트랩 card-body 클래스 추가
  postDescriptions.textContent = post.description;

  // 게시물 댓글 수 및 입력란 생성
  const postComments = document.createElement("div");
  postComments.className = "post-comments card-footer"; // 부트스트랩 card-footer 클래스 추가
  postComments.innerHTML = `
        <div class="font-weight-bold comments-all-btn">
          <span>댓글 모두 보기</span>
          <div class="comments-container">@닉네임 : 더미 댓글</div>
          <div class="comments-container">@닉네임 : 더미 댓글</div>
          <div class="comments-container">@닉네임 : 더미 댓글</div>
        </div>
        <div class="input-group">
            <input type="text" placeholder="댓글 달기" id="comment-form" class="form-control" />
            <div class="input-group-append">
                <img src="/image/comment-btn-icon.svg" id="post-comment-btn" class="btn mr-2" />
            </div>
        </div>
    `;

  // postInner에 요소들 추가
  postInner.appendChild(postHeader);
  postInner.appendChild(postMedia);
  postInner.appendChild(postButtons);
  postInner.appendChild(postLikes);
  postInner.appendChild(postDescriptions);
  postInner.appendChild(postComments);

  // postInner를 postElement에 추가
  postElement.appendChild(postInner);

  // 생성된 게시물을 컨테이너에 추가
  postContainer.appendChild(postElement);

  // 게시물 생성이 완료된 후, 마지막 게시물인 경우 버튼 생성
  if (i === n - 1) {
    const nextPost = document.createElement("div");
    nextPost.id = "more-post-btn";
    nextPost.innerHTML =
      '<img src="/image/more-post-btn.svg" id="more-post-icon" class="btn mr-2" />';
    nextPost.onclick = function () {
      // 추가 작업 수행
    };
    postContainer.appendChild(nextPost);
  }
}

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

const goToBottomBtn = document.createElement("button");
goToTopBtn.id = "goToBottomBtn";
goToTopBtn.style.display = "none";
goToTopBtn.innerHTML = "<img src='/image/goToTop-btn.svg' />";
goToTopBtn.onclick = function () {
  window.scrollTo({
    top: 0,
    behavior: "smooth",
  });
};

function changeImage() {
  document.getElementById("logo-btn").src = "/image/animated-logo.gif";
}

function restoreImage() {
  document.getElementById("logo-btn").src = "/image/logo-btn-icon.png";
}
