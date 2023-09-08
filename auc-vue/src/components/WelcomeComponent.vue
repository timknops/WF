<template>
  <div class="container-fluid bg-body px-5 pt-3">
    <div class="row">
      <div class="col-3 flex-column">
        <div class="bg-body-tertiary p-4 rounded-3 border">
          <h4>Pick a date</h4>
          <input type="date" id="date" />
          <h4>Pick a category</h4>
        </div>
      </div>
      <div class="col">
        <div class="bg-body-tertiary p-4 rounded-3 border">
          <h4 class="mb-3">Auctions that expire today</h4>
          <div class="flex gap-5">
            <img
              v-for="image in images"
              :key="image.id"
              :src="image.src"
              alt="random auction image"
              class="rounded-3"
            />
          </div>
        </div>
      </div>
      <div class="col-3">
        <div class="bg-body-tertiary p-4 rounded-3 border">
          <h4>{{ randomAuction.title }}</h4>
          <p>{{ randomAuction.description }}</p>
          <h6>Auction closes at: {{ randomAuction.dueDate }}</h6>
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
        {
          id: 1,
          src: "https://fastly.picsum.photos/id/649/200/200.jpg?hmac=tj148mYv7Me5ctSyCePc_TNjma4W3n3RwnqJcIogLoI",
        },
        {
          id: 2,
          src: "https://fastly.picsum.photos/id/804/200/200.jpg?hmac=73qw3Bnt67aOsdWd033BvfX9Gq0gIJ6FSL3Dp3gA97E",
        },
        {
          id: 3,
          src: "https://fastly.picsum.photos/id/560/200/200.jpg?hmac=Dqou6QpKCTK2srRsCRhlIxLQHvFL7zz6UocOb3UkpwI",
        },
        {
          id: 4,
          src: "https://fastly.picsum.photos/id/451/200/200.jpg?hmac=ZxfTfjtPhONiVjaPd1HG3lQuTRy_puiwOF6qStaMe7g",
        },
        {
          id: 5,
          src: "https://fastly.picsum.photos/id/879/200/200.jpg?hmac=_4fWz44KoPcfzc5VRuEhms_-fXjdx1VsijYO3xVD9b0",
        },
      ],
    };
  },
};
</script>

<style scoped>
.col-3 {
  width: 400px;
}

.container-fluid {
  height: 100vh;
}

img {
  width: 250px;
}
</style>
