import {Offer} from "@/models/offer";

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
      console.log(response, !response.bodyUsed ? await response.text() : '');
      return null;
    }
  }

  /**
   * return all offers from the backend
   * @return {Promise<[Offer]> || null} a list of offers or null
   */
  async asyncFindAll() {
    const data = await this.fetchJSON(this.resourceURL);
    return data?.map(offer => Offer.copyConstructor(offer))
  }

  /**
   * Return a single offer by its id
   * @param {Number} id the id of an offer
   * @return {Promise<Offer> || null}
   */
  async asyncFindById(id) {
    const offer = await this.fetchJSON(`${this.resourceURL}/${id}`)
    return Offer.copyConstructor(offer);
  }

  /**
   * return a newly added offer, or the updated offer
   * @param {Offer} offer
   * @return {Promise<Offer>}
   */
  async asyncSave(offer){
    if (offer.id === 0) {
      const newOffer = await this.fetchJSON(this.resourceURL, {method: "POST", body: JSON.stringify(offer)})
      return Offer.copyConstructor(newOffer);
    } else {
      const updatedOffer = await this.fetchJSON(`${this.resourceURL}/${offer.id}`, {method: "PUT", body: JSON.stringify(offer)})
      return Offer.copyConstructor(updatedOffer);
    }

  }

  async asyncDeleteById(id) {
    const deletedOffer = await this.fetchJSON(`${this.resourceURL}/${id}`, {method: "DELETE"})
    return Offer.copyConstructor(deletedOffer);
  }
}