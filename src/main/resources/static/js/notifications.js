// Sistema de notificaciones toast profesional reutilizable

console.log('Sistema de notificaciones cargado');

function showToast(type, title, message, duration = 5000) {
    console.log('Mostrando notificacion:', type, title, message);
    
    // Crear contenedor si no existe
    let container = document.getElementById('toastContainer');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toastContainer';
        container.className = 'toast-container';
        document.body.appendChild(container);
        console.log('Contenedor de notificaciones creado');
    }
    
    const icons = {
        success: '✓',
        error: '✕',
        info: 'ℹ',
        warning: '⚠'
    };
    
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `
        <div class="toast-icon">${icons[type]}</div>
        <div class="toast-content">
            <div class="toast-title">${title}</div>
            <div class="toast-message">${message}</div>
        </div>
        <button class="toast-close" onclick="closeToast(this)">×</button>
        <div class="toast-progress"></div>
    `;
    
    container.appendChild(toast);
    console.log('Toast agregado al contenedor');
    
    // Auto cerrar despues del tiempo especificado
    setTimeout(() => {
        closeToast(toast.querySelector('.toast-close'));
    }, duration);
}

function closeToast(button) {
    const toast = button.closest('.toast');
    if (toast) {
        toast.classList.add('hiding');
        setTimeout(() => {
            toast.remove();
        }, 400);
    }
}

// Verificar parametros de URL al cargar la pagina
window.addEventListener('DOMContentLoaded', () => {
    console.log('DOM cargado, verificando parametros de URL');
    const urlParams = new URLSearchParams(window.location.search);
    console.log('Parametros de URL:', urlParams.toString());
    
    // Mensajes de exito
    if (urlParams.has('registroExitoso')) {
        console.log('Parametro registroExitoso encontrado');
        showToast('success', 'Registro Exitoso', 'Tu cuenta ha sido creada correctamente. Ahora puedes iniciar sesion.');
    }
    if (urlParams.has('libroCreado')) {
        console.log('Parametro libroCreado encontrado');
        showToast('success', 'Libro Agregado', 'El libro ha sido agregado exitosamente al catalogo.');
    }
    if (urlParams.has('libroActualizado')) {
        console.log('Parametro libroActualizado encontrado');
        showToast('success', 'Libro Actualizado', 'La informacion del libro ha sido actualizada correctamente.');
    }
    if (urlParams.has('libroEliminado')) {
        console.log('Parametro libroEliminado encontrado');
        showToast('success', 'Libro Eliminado', 'El libro ha sido eliminado del catalogo.');
    }
    if (urlParams.has('prestamoSolicitado')) {
        console.log('Parametro prestamoSolicitado encontrado');
        showToast('success', 'Prestamo Solicitado', 'Tu solicitud de prestamo ha sido procesada exitosamente.');
    }
    
    // Mensajes de error
    if (urlParams.has('error')) {
        const errorMsg = urlParams.get('error');
        console.log('Parametro error encontrado:', errorMsg);
        showToast('error', 'Error', errorMsg || 'Ha ocurrido un error. Por favor intenta nuevamente.');
    }
    
    // Limpiar parametros de la URL sin recargar (despues de un delay para que se vea la notificacion)
    if (urlParams.toString()) {
        setTimeout(() => {
            const cleanUrl = window.location.pathname;
            window.history.replaceState({}, document.title, cleanUrl);
            console.log('URL limpiada');
        }, 500);
    }
});

// Funcion de prueba para verificar que funciona
window.testNotification = function() {
    showToast('success', 'Prueba', 'Esta es una notificacion de prueba');
};

console.log('Puedes probar las notificaciones escribiendo: testNotification()');
