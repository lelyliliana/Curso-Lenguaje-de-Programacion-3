import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '15s',

    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<500'],
        checks: ['rate>0.99'],
    },
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