import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 5,
    duration: '10s',
};

export default function () {

    const response = http.get(
        'http://localhost:8080/mensaje'
    );

    check(response, {
        'estado HTTP es 200': (r) => r.status === 200,
        'respuesta contiene el texto esperado': (r) =>
            r.body.includes('Endpoint utilizado para generar métricas'),
    });

    sleep(1);
}