
document.addEventListener('DOMContentLoaded', () => {
    const themeToggleBtn = document.getElementById('theme-toggle');
    const icon = themeToggleBtn ? themeToggleBtn.querySelector('i') : null;

    // Cargar preferencia guardada
    const savedTheme = localStorage.getItem('theme') || 'light';
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-mode');
        if (icon) {
            icon.classList.remove('fa-moon');
            icon.classList.add('fa-sun');
        }
    }

    if (themeToggleBtn) {
        themeToggleBtn.addEventListener('click', () => {
            document.body.classList.toggle('dark-mode');
            const isDark = document.body.classList.contains('dark-mode');

            // Guardar preferencia
            localStorage.setItem('theme', isDark ? 'dark' : 'light');

            // Cambiar icono
            if (icon) {
                if (isDark) {
                    icon.classList.remove('fa-moon');
                    icon.classList.add('fa-sun');
                } else {
                    icon.classList.remove('fa-sun');
                    icon.classList.add('fa-moon');
                }
            }

            // Avisar a Three.js del cambio
            window.dispatchEvent(new CustomEvent('themeChanged', {
                detail: { theme: isDark ? 'dark' : 'light' }
            }));
        });
    }
});
