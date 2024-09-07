export class AppJwt {
  sub: string; // Subject (User identifier)
  iat: number; // Issued At timestamp
  exp: number; // Expiration time timestamp
  authority: string;

  constructor(sub: string, iat: number, exp: number, authority: string) {
    this.sub = sub;
    this.iat = iat;
    this.exp = exp;
    this.authority = authority;
  }
}
