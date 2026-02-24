
document.addEventListener('DOMContentLoaded', () => {
    // Verificar si Three.js cargo
    if (typeof THREE === 'undefined') {
        console.error('Three.js no esta cargado');
        return;
    }

    // Create scene, camera, renderer
    const scene = new THREE.Scene();
    // scene.background = new THREE.Color(0xf8fafc); // Removed to allow CSS background to show
    // scene.fog = new THREE.FogExp2(0xf8fafc, 0.002); // Removed fog for clarity with gradient

    const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    camera.position.z = 50;

    const renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setClearColor(0x000000, 0); // Transparent background

    // Crear contenedor para el canvas si no existe
    let container = document.getElementById('canvas-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'canvas-container';
        container.style.position = 'fixed';
        container.style.top = '0';
        container.style.left = '0';
        container.style.width = '100%';
        container.style.height = '100%';
        container.style.zIndex = '-1';
        container.style.opacity = '0.5';
        container.style.pointerEvents = 'none'; // Permite clics debajo
        document.body.prepend(container);
    }
    container.innerHTML = ''; // Clear previous canvas if any
    container.appendChild(renderer.domElement);

    // --- GEOMETRY: Network of Knowledge (Nodes and Lines) ---

    const particlesGeometry = new THREE.BufferGeometry();
    const particleCount = 150; // Adjusted count

    const positions = new Float32Array(particleCount * 3);
    const colors = new Float32Array(particleCount * 3);

    // Blue Color (Matches CSS --accent-color: #2563eb)
    const color = new THREE.Color(0x2563eb);

    for (let i = 0; i < particleCount; i++) {
        // Spread particles
        positions[i * 3] = (Math.random() * 2 - 1) * 100;
        positions[i * 3 + 1] = (Math.random() * 2 - 1) * 60;
        positions[i * 3 + 2] = (Math.random() * 2 - 1) * 40;

        colors[i * 3] = color.r;
        colors[i * 3 + 1] = color.g;
        colors[i * 3 + 2] = color.b;
    }

    particlesGeometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
    particlesGeometry.setAttribute('color', new THREE.BufferAttribute(colors, 3));

    // Material de los puntos
    const pointsMaterial = new THREE.PointsMaterial({
        size: 0.6,
        vertexColors: true,
        transparent: true,
        opacity: 0.8
    });

    const particleSystem = new THREE.Points(particlesGeometry, pointsMaterial);
    scene.add(particleSystem);

    // Lineas que conectan particulas cercanas
    const lineMaterial = new THREE.LineBasicMaterial({
        color: 0x2563eb, // Color azul
        transparent: true,
        opacity: 0.15
    });

    const linesGeometry = new THREE.BufferGeometry();
    const linesMesh = new THREE.LineSegments(linesGeometry, lineMaterial);
    scene.add(linesMesh);

    // --- CICLO DE ANIMACION ---

    // Interaccion con el mouse
    let mouseX = 0;
    let mouseY = 0;
    const windowHalfX = window.innerWidth / 2;
    const windowHalfY = window.innerHeight / 2;

    document.addEventListener('mousemove', (event) => {
        mouseX = (event.clientX - windowHalfX) * 0.05;
        mouseY = (event.clientY - windowHalfY) * 0.05;
    });

    function animate() {
        requestAnimationFrame(animate);

        const time = Date.now() * 0.0005; // Slow movement

        // Movimiento suave de las particulas
        const positions = particleSystem.geometry.attributes.position.array;

        for (let i = 0; i < particleCount; i++) {
            // Movimiento suave de ondas
            positions[i * 3 + 1] += Math.sin(time + positions[i * 3] * 0.05) * 0.02;
        }
        particleSystem.geometry.attributes.position.needsUpdate = true;

        // Actualizar conexiones
        updateLines();

        // Movimiento de camara segun el mouse
        camera.position.x += (mouseX - camera.position.x) * 0.02;
        camera.position.y += (-mouseY - camera.position.y) * 0.02;
        camera.lookAt(scene.position);

        renderer.render(scene, camera);
    }

    function updateLines() {
        const positions = particleSystem.geometry.attributes.position.array;
        const linePositions = [];

        // Find connections
        const connectDistance = 25;

        for (let i = 0; i < particleCount; i++) {
            for (let j = i + 1; j < particleCount; j++) {
                const dx = positions[i * 3] - positions[j * 3];
                const dy = positions[i * 3 + 1] - positions[j * 3 + 1];
                const dz = positions[i * 3 + 2] - positions[j * 3 + 2];
                const distSq = dx * dx + dy * dy + dz * dz;

                if (distSq < connectDistance * connectDistance) {
                    // Create connection
                    linePositions.push(
                        positions[i * 3], positions[i * 3 + 1], positions[i * 3 + 2],
                        positions[j * 3], positions[j * 3 + 1], positions[j * 3 + 2]
                    );
                }
            }
        }

        linesMesh.geometry.setAttribute('position', new THREE.Float32BufferAttribute(linePositions, 3));
    }

    animate();

    // Manejador de cambio de tamano
    window.addEventListener('resize', () => {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
    });
});

