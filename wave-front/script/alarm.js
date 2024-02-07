document.addEventListener("DOMContentLoaded", function () {
  const alarmModalBtn = document.getElementById("alarm-modal-btn");
  const alarmModal = new bootstrap.Modal(
    document.getElementById("alarm-modal")
  );

  alarmModalBtn.addEventListener("click", function () {
    alarmModal.toggle();
  });
});

// received-alarm 요소에 더미 데이터 개수 표시
const receivedAlarmElement = document.getElementById("received-alarm");
const numberOfAlarms = 100; // 더미 데이터 개수
receivedAlarmElement.textContent = `받은 알림 : ${numberOfAlarms}개`;

// alarm-erase-all 요소에 알림이 있을 경우 모두 지우기 버튼 생성
const alarmEraseAllElement = document.getElementById("alarm-erase-all");
if (numberOfAlarms > 0) {
  const eraseAllButton = document.createElement("button");
  eraseAllButton.textContent = "모두 읽음";
  eraseAllButton.addEventListener("click", () => {
    // 삭제 버튼 클릭 시 알림 모두 삭제하는 함수 호출
    removeAllAlarms();
  });
  alarmEraseAllElement.appendChild(eraseAllButton);
}

// alarm-content 요소에 더미 데이터 동적으로 생성
const alarmContentElement = document.getElementById("alarm-content");
for (let i = 0; i < numberOfAlarms; i++) {
  const alarmItem = document.createElement("div");
  alarmItem.classList.add("alarm-item");
  alarmItem.textContent = `더미 알림 ${i + 1}`;

  // 더미 데이터 옆에 삭제 버튼 생성
  const deleteButton = document.createElement("button");
  deleteButton.innerHTML = "<img src='/image/read-btn.svg' />";
  deleteButton.addEventListener("click", () => {
    // 삭제 버튼 클릭 시 해당 알림 삭제하는 함수 호출
    deleteAlarm(alarmItem);
  });
  alarmItem.appendChild(deleteButton);

  alarmContentElement.appendChild(alarmItem);
}

// 알림 모두 삭제하는 함수
function removeAllAlarms() {
  alarmContentElement.innerHTML = ""; // 모든 알림 삭제
  receivedAlarmElement.textContent = "받은 알림 : 0개"; // 알림 개수 표시 업데이트
}

// 특정 알림 삭제하는 함수
function deleteAlarm(alarmItem) {
  alarmItem.remove(); // 해당 알림 삭제
  const remainingAlarms = document.querySelectorAll(".alarm-item").length;
  receivedAlarmElement.textContent = `받은 알림 : ${remainingAlarms}개`; // 알림 개수 표시 업데이트
}
