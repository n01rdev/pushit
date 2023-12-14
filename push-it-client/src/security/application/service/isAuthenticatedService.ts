export class IsAuthenticatedService {
    check(): boolean {
        let authToken = localStorage.getItem('authToken');
        return authToken !== undefined && authToken !== null && authToken !== '';
    }
}