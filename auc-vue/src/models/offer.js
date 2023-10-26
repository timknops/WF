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
   * @param status {string}
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

  /**
   * Make a copy of an offer class
   * @param {Offer} offer the offer class to be deep cloned
   * @return {Offer}
   */
  static copyConstructor(offer) {
    if (offer == null) return null;

    const copy = Object.assign(new Offer(), offer);

    //Clone the complex datatypes in offer like date
    copy.sellDate = new Date(offer.sellDate);
    return copy;
  }

  /**
   * Create an empty offer.
   * @returns {Offer}  an empty offer
   */
  static createEmptyOffer() {
    return new Offer(0, "", Offer.Status.NEW, "", new Date(), 0);
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

    /*
    Get a random status value out of the status object. Object.values returns a arraylist of all the values.
    Then you access a random value between one and the length of the values array.
    */
    const status = Object.values(Offer.Status)[
      Math.floor(Math.random() * Object.values(Offer.Status).length)
    ];

    let sellDate;

    // create a date object from another Date object. This date object is the current date with a month less or more
    // with setMonth you can change the month of the date object you called the function on.
    const previousMonth = new Date(
      new Date().setMonth(new Date().getMonth() - 1)
    );
    const nextMonth = new Date(new Date().setMonth(new Date().getMonth() + 1));
    if (
      status === Offer.Status.CLOSED ||
      status === Offer.Status.EXPIRED ||
      status === Offer.Status.SOLD
    ) {
      sellDate = Offer.randomDate(previousMonth, new Date());
    } else {
      sellDate = Offer.randomDate(new Date(), nextMonth);
    }

    let valueHighestBid;
    // if status is now, the bidding value should be 0
    if (status === Offer.Status.NEW) {
      valueHighestBid = 0;
    } else {
      valueHighestBid = this.valueBetween(5, 3000);
    }
    return new Offer(id, title, status, description, sellDate, valueHighestBid);
  }

  /**
   * get a random date between the start date and end Date
   * @param start {Date} - the earliest date the function can return
   * @param end {Date} - the latest date the function can return
   */
  static randomDate(start, end) {
    //convert date to value in milliseconds, to make it able to use it in Math.random
    const startInMilliseconds = start.valueOf();
    const endInMilliseconds = end.valueOf();
    return new Date(Offer.valueBetween(endInMilliseconds, startInMilliseconds));
  }

  /**
   * Get a value between the given max and min including the min and max
   * @param min {number} - the mininum value to be returned
   * @param max {number} - the maximum value to be returned
   * @return {number} random value between min and max
   */
  static valueBetween(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
  }

  /**
   * Check if to offers are the same
   * @param {Offer} other offer to compare this with.
   * @return {boolean} true if equal false if objects are not equal
   */
  equals(other) {
    if (!(other instanceof Offer)) return false;
    return (
      this.id === other.id &&
      this.title === other.title &&
      this.description === other.description &&
      this.valueHighestBid === other.valueHighestBid &&
      this.status === other.status &&
      this.sellDate?.getTime() === other.sellDate?.getTime() //check if date are the same by comparing the time in milliseconds
    );
  }
}
