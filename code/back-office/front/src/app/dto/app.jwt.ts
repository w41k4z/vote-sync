export class AppJwt {
  sub: string; // Subject (User identifier)
  iat: number; // Issued At timestamp
  exp: number; // Expiration time timestamp
  authority: string;
  userInformation: string;

  constructor(
    sub: string,
    iat: number,
    exp: number,
    authority: string,
    userInformation: string
  ) {
    this.sub = sub;
    this.iat = iat;
    this.exp = exp;
    this.authority = authority;
    this.userInformation = userInformation;
  }
}
