export class RefreshTokenPayloadResponse {
  newAccessToken: string;

  constructor(accessToken: string) {
    this.newAccessToken = accessToken;
  }
}
