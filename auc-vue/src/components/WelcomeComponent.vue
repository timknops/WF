<template>
  <div class="container-fluid bg-body px-5 py-3 h-100">
    <div class="row">
      <div class="col-xl-3 flex-column mt-xl-0 mt-2">
        <div class="bg-body-tertiary p-4 rounded-3 border h-100">
          <h4 class="mb-3 fw-bold">Pick a date</h4>
          <input type="date" id="date" class="form-control" />
          <h4 class="mb-3 mt-4 fw-bold">Pick a category</h4>
          <p v-for="category in categories" :key="category" class="my-4">
            <a href="#" :key="category" class="bg-primary-subtle p-2 rounded-3">
              - {{ category }}</a
            >
          </p>
        </div>
      </div>
      <div class="col-xl my-xl-0 my-4">
        <div class="bg-body-tertiary p-4 rounded-3 border">
          <h4 class="mb-3 fw-bold">Auctions that expire today</h4>
          <div class="img-container w-100 gap-2">
            <img
              v-for="image in images"
              :key="image"
              :src="image"
              alt="random auction image"
              class="rounded-3"
            />
          </div>
        </div>
      </div>
      <div class="col-xl-3 mb-xl-0 mb-2">
        <div class="bg-body-tertiary p-4 rounded-3 border h-100">
          <h4 class="mb-3 fw-bold">{{ randomAuction.title }}</h4>
          <p>{{ randomAuction.description }}</p>
          <h6 class="fw-bold">Action closes at: {{ randomAuction.dueDate }}</h6>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "WelcomeComponent",
  data() {
    return {
      randomAuction: {
        title: "Random Auction Title",
        description:
          "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A beatae eligendi mollitia numquam optio provident quibusdam sequi sunt suscipit. " +
          "Error excepturi hic sit unde. Ad aspernatur delectus soluta. Mollitia, quas!",
        dueDate: "23-08-2023",
      },
      images: [
        "https://fastly.picsum.photos/id/649/200/200.jpg?hmac=tj148mYv7Me5ctSyCePc_TNjma4W3n3RwnqJcIogLoI",
        "https://fastly.picsum.photos/id/804/200/200.jpg?hmac=73qw3Bnt67aOsdWd033BvfX9Gq0gIJ6FSL3Dp3gA97E",
        "https://fastly.picsum.photos/id/560/200/200.jpg?hmac=Dqou6QpKCTK2srRsCRhlIxLQHvFL7zz6UocOb3UkpwI",
        "https://fastly.picsum.photos/id/451/200/200.jpg?hmac=ZxfTfjtPhONiVjaPd1HG3lQuTRy_puiwOF6qStaMe7g",
        "https://fastly.picsum.photos/id/879/200/200.jpg?hmac=_4fWz44KoPcfzc5VRuEhms_-fXjdx1VsijYO3xVD9b0",
      ],
      categories: [
        "Antiques",
        "Cars ",
        "Event Tickets",
        "Holidays",
        "Collectables",
      ],
    };
  },
};
</script>

<style scoped>
.container-fluid {
  max-width: 1920px;
}

img {
  width: 100%;
  cursor: pointer;
  filter: brightness(90%);
  transition: all 0.2s ease-in-out;
}
img:hover {
  filter: brightness(100%);
  box-shadow: var(--bs-box-shadow);
}

.img-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(210px, 1fr));
}

a {
  text-decoration: none;
}
a:hover {
  color: var(--bs-primary-text-emphasis);
  background-color: var(--bs-gray-300) !important;
}
</style>
