export class Offer {
  /**
   * A enum for all statuses a offer can have
   * @type {Readonly<{FOR_SALE: string, WITHDRAWN: string, NEW: string, SOLD: string, DELIVERED: string, CLOSED: string, EXPIRED: string, PAID: string}>}
   */
  static Status = Object.freeze({
    NEW: "NEW",
    FOR_SALE: "FOR_SALE",
    SOLD: "SOLD",
    PAID: "PAID",
    DELIVERED: "DELIVERED",
    CLOSED: "CLOSED",
    EXPIRED: "EXPIRED",
    WITHDRAWN: "WITHDRAWN",
  });

  /**
   * constructor for creating a new Offer
   * @param id {number}
   * @param title {string}
   * @param status {Offer.Status}
   * @param description {string}
   * @param sellDate {Date}
   * @param valueHighestBid {number}
   */
  constructor(id, title, status, description, sellDate, valueHighestBid) {
    this.id = id;
    this.title = title;
    this.status = status;
    this.description = description;
    this.sellDate = sellDate;
    this.valueHighestBid = valueHighestBid;
  }

  static createSampleOffer(id) {
    const RANDOM_TITLES = [
      "Lamp",
      "Clock",
      "Cabinet",
      "Toolbox",
      "Laptop",
      "Bicycle",
      "Car",
      "Couch",
    ];
    const RANDOM_DESCRIPTIONS = [
      "A nice modern lamp",
      "A antique analoge clock with winding mechanism",
      "A big closet for hanging and stacking clothes",
      "A toolbox, containing screwdrivers hamers and storage for nails and screws",
      "A Asus tuf gaming laptop with 1 year uses",
      "A learning bicycle for children",
      "A ford focus, from 2017",
      "A rustic couch with laying area",
    ];
    //random index for the title and description array, so that the description corresponds to the title.
    const randomIndex = Math.floor(Math.random() * RANDOM_TITLES.length);

    const title = RANDOM_TITLES[randomIndex];
    const description = RANDOM_DESCRIPTIONS[randomIndex];

    //get a random value out of the offer status object.
    const status = Object.values(Offer.Status)[
      Math.floor(Math.random() * Object.values(Offer.Status).length)
    ];
  }

  /**
   *
   * @param start {Date}
   * @param end {Date}
   */
  #randomDate(start, end) {}
}
