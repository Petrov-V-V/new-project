const backgroundImages = [
  'books.jpg',
  'summer_books.jpg',
];
const backgroundText = [
  'Популярные новинки от известных авторов уже в продаже в BOOKISH',
  'Сезон летнего чтения в BOOKISH\nСкидки до 25%',
];


var myTimer = setInterval(() => {
  switchBackground('right');
  switchText('right');
  updateCarouselIndicators();
}, 5000);


let currentTextIndex = 0;
let currentBackgroundIndex = 0;
const topBackground = document.getElementById('top-background');
const leftButton = document.getElementById('left-button');
const rightButton = document.getElementById('right-button');
const carouselIndicators = document.getElementById('carousel-indicators');
const backgroundTitle = document.getElementById('background-title');

function setBackground() {
  topBackground.classList.add('fade-out');
  setTimeout(() => {
    topBackground.classList.remove('fade-out');
    topBackground.style.backgroundImage = `url(${backgroundImages[currentBackgroundIndex]})`;
    topBackground.classList.add('appear');
    setTimeout(() => {
      topBackground.classList.remove('appear');
      clearInterval(myTimer);
      myTimer = setInterval(() => {
        switchBackground('right');
        switchText('right');
        updateCarouselIndicators();
      }, 5000);
    }, 500);
  }, 500);
}

function setText() {
  setTimeout(() => {
    backgroundTitle.innerHTML = backgroundText[currentBackgroundIndex];
  }, 500);
}

function switchBackground(direction) {
  if (direction === 'left') {
    currentBackgroundIndex = (currentBackgroundIndex - 1 + backgroundImages.length) % backgroundImages.length;
  } else if (direction === 'right') {
    currentBackgroundIndex = (currentBackgroundIndex + 1) % backgroundImages.length;
  }
  setBackground();
}

function updateCarouselIndicators() {
  carouselIndicators.innerHTML = '';
  for (let i = 0; i < backgroundImages.length; i++) {
    const indicator = document.createElement('div');
    indicator.classList.add('indicator');
    if (i === currentBackgroundIndex) {
      indicator.classList.add('active');
    }
    indicator.addEventListener('click', () => {
      switchBackgroundToIndex(i);
    });
    carouselIndicators.appendChild(indicator);
  }
}

function switchBackgroundToIndex(index) {
  if (index !== currentBackgroundIndex) {
    const direction = index < currentBackgroundIndex ? 'left' : 'right';
    currentBackgroundIndex = index;
    setBackground();
    setText();
    updateCarouselIndicators();
  }
}

function switchText(direction) {
  if (direction === 'left') {
    currentTextIndex = (currentTextIndex - 1 + backgroundText.length) % backgroundText.length;
  } else if (direction === 'right') {
    currentTextIndex = (currentTextIndex + 1) % backgroundText.length;
  }
  setText();
}

leftButton.addEventListener('click', () => {switchBackground('left'); switchText('left'); updateCarouselIndicators()});
rightButton.addEventListener('click', () => {switchBackground('right'); switchText('right'); updateCarouselIndicators()});

setBackground();
updateCarouselIndicators();