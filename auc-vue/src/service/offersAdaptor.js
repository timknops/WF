import { Offer } from "@/models/offer";

export class OffersAdaptor {
  resourceURL;

  constructor(resourceURL) {
    this.resourceURL = resourceURL;
  }

  async fetchJSON(url, options = null) {
    let response = await fetch(url, options);
    if (response.ok) {
      return await response.json();
    } else {
      //Log the error if there is an error provided by the http response body.
      console.log(response, !response.bodyUsed ? await response.text() : "");
      return null;
    }
  }

  /**
   * return all offers from the backend
   * @return {Promise<[Offer]> || null} a list of offers or null
   */
  async asyncFindAll(queryParams = null) {
    const url = this.appendQueryParams(this.resourceURL, queryParams);
    const data = await this.fetchJSON(url);
    return data?.map((offer) => Offer.copyConstructor(offer));
  }

  /** Appends the query params in to the url. */
  appendQueryParams(url, queryParams) {
    if (queryParams) {
      const urlParams = new URLSearchParams(queryParams);
      url += "?" + urlParams.toString();
    }
    return url;
  }

  /**
   * Return a single offer by its id
   * @param {Number} id the id of an offer
   * @return {Promise<Offer> || null}
   */
  async asyncFindById(id) {
    const offer = await this.fetchJSON(`${this.resourceURL}/${id}`);
    return Offer.copyConstructor(offer);
  }

  /**
   * return a newly added offer, or the updated offer
   * @param {Offer} offer
   * @return {Promise<Offer>}
   */
  async asyncSave(offer) {
    if (offer.id === 0) {
      const newOffer = await this.fetchJSON(this.resourceURL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(offer),
      });

      return Offer.copyConstructor(newOffer);
    } else {
      const updatedOffer = await this.fetchJSON(
        `${this.resourceURL}/${offer.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(offer),
        }
      );

      return Offer.copyConstructor(updatedOffer);
    }
  }

  /**
   * Delete an offer by its id
   * @param {Number} id the id of an offer
   * @return {Promise<Offer> || null}  the deleted offer or null
   */
  async asyncDeleteById(id) {
    const deletedOffer = await this.fetchJSON(`${this.resourceURL}/${id}`, {
      method: "DELETE",
    });
    return Offer.copyConstructor(deletedOffer);
  }

  /**
   * Add a bid to an offer.
   * @param {Object} bid
   * @return {Promise<Object>} the newly added bid
   */
  async asyncAddBid(bid) {
    const newBid = await this.fetchJSON(
      `${this.resourceURL}/${bid.offer.id}/bids`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(bid),
      }
    );

    return newBid;
  }
}
