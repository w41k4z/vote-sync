export class ApiResponse<P> {
  payload?: P;
  message?: string;
  errors?: { [key: string]: string[] };

  constructor(
    payload?: P,
    message?: string,
    errors?: { [key: string]: string[] }
  ) {
    this.payload = payload;
    this.message = message;
    this.errors = errors;
  }
}
