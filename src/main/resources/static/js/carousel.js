let currentSlide = 0;

function showSlide(index) {
    const slides = document.querySelectorAll('.carousel-slide');
    if (slides.length === 0) return;

    if (index >= slides.length) currentSlide = 0;
    else if (index < 0) currentSlide = slides.length - 1;
    else currentSlide = index;

    slides.forEach(slide => slide.classList.remove('active'));
    slides[currentSlide].classList.add('active');
}

function changeSlide(step) {
    showSlide(currentSlide + step);
}

document.addEventListener('DOMContentLoaded', () => {
    showSlide(0);
    setInterval(() => changeSlide(1), 5000);
});