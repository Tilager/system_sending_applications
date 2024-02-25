<template>
  <div class="container">
    <div class="card mt-5" style="width: 23rem;" v-for="course in courses">
      <div class="card-header">
        <h4>
          {{ course.name }}
        </h4>
      </div>
      <div class="card-body">
        <h5> {{ course.name }} </h5>
        <p> Описание: {{ course.description }} </p>
        <p> Язык: {{ course.language }} </p>
        <p> Длительность: {{ course.duration }} часов </p>
        <p> Цена: {{ course.price === 0 ? 'Бесплатно' : course.price + ' рублей' }} </p>
        <button class="btn btn-primary mt-2" @click="sendApplication(course.id)">Отправить заявку</button>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>

<script>
  import axios from "axios";
  import {mapGetters} from "vuex";

  export default {
    name: "applications",
    data() {
      return {
        courses: []
      }
    },
    mounted() {
      axios.get('http://localhost:8081/api/courses/all').then(res => {
        this.courses = res.data
      })
    },
    methods: {
      sendApplication(id) {
        let data = {
          client: this.currentUser.client,
          course: this.courses.find(c => c.id === id)
        }

        axios.post("http://localhost:8081/api/application/create",
            data)
            .then(() => {
              alert('Заявка успешно отправлена!')
            }).catch(err => {
                if(err.response.status === 409) {
                  alert(err.response.data)
                }
                else {
                  console.error(err.response.data)
                }
            })
      }
    },
    computed: mapGetters(['currentUser'])
  }
</script>

<style scoped>
  .card-body p {
    margin-bottom: 6px;
  }
</style>