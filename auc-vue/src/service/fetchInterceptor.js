import fetchIntercept from "fetch-intercept";

export class FetchInterceptor {
  static theInstance;
  sessionService;
  unregister;
  router;

  constructor(sessionService, router) {
    this.router = router;
    FetchInterceptor.theInstance = this;
    this.sessionService = sessionService;
    this.unregister = fetchIntercept.register(this);
  }

  request(url, options = {}) {
    const token = FetchInterceptor.theInstance.sessionService.currentToken;
    if (!token) return [url, options];

    // Check if options.headers is defined, otherwise, initialize it as an empty object
    const headers = options
      ? { ...options.headers, Authorization: token }
      : { Authorization: token };

    return [url, { ...options, headers }];
  }

  response(response) {
    if (response.status >= 400 && response.status < 600) {
      FetchInterceptor.theInstance.handleErrorInResponse(response);
    }

    return response;
  }

  requestError(error) {
    return Promise.reject(error);
  }

  responseError(error) {
    return Promise.reject(error);
  }

  async handleErrorInResponse(response) {
    if (response.status === 401) {
      // If the response is a 401 we redirect to the login page.
      this.router.push("/sign-in");
    } else if (response.status !== 406) {
      // If the response is not a 401, we throw the error.
      throw response;
    }
  }
}
