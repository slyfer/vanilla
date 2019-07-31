export class JwtAuthenticationResponse {
  constructor(public accessToken: string, public tokenType: string) {
  }
}
