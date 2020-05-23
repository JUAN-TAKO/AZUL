const refresh_rate = 5;
let last_action = 0;

const reset = () => {last_action = 0;};

setInterval( () => {
	last_action++;
	if(last_action >= refresh_rate && document.readyState === 'complete'){
		window.location.reload();
		reset();
	}
}, 1000);

window.addEventListener('click', reset);
window.addEventListener('mousemove', reset);
document.addEventListener('touchmove', reset);
document.addEventListener('touchend', reset);
