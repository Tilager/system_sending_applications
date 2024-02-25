<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        Изменение группы
      </div>
      <div class="card-body">
        <div class="alert alert-success" role="alert" v-if="alert === 'success'">
          Данные успешно обновлены!
        </div>
        <div class="alert alert-danger" role="alert" v-if="alert === 'danger'">
          Проверьте все поля!
        </div>

        <div class="mb-3">
          <label for="">Название</label>
          <input v-model="model.group.name"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.group.name.$dirty && v$.model.group.name.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Вместимость</label>
          <input v-model="model.group.capacity"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.group.capacity.$dirty && v$.model.group.capacity.$error)}">
        </div>

        <div class="mb-3">
          <label for="">Курс</label>

          <select class="form-control"
                  :class="{'is-invalid': (v$.model.group.course.$dirty && v$.model.group.course.$error)}"
                  @change="setCourse"
          >
            <option disabled selected value> Выберите курс </option>
            <option v-for="course in courses"
                    :value="course.id" :selected="course.id === model.group.course.id"> {{ course.name }} ({{ course.language }})</option>
          </select>
        </div>

        <div class="mb-3">
          <button class="btn btn-primary" @click="updateGroups" type="button">Сохранить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import useVuelidate from "@vuelidate/core";
  import {email, integer, minValue, numeric, required} from "@vuelidate/validators";

  export default {
    name: 'groupsEdit',
    data() {
      return {
        alert: '',
        model: {
          group: {
            name: '',
            capacity: null,
            course: null
          }
        },
        courses: []
      }
    },
    mounted() {
      this.getGroup(this.$route.params.id)

      axios.get('http://localhost:8081/api/courses/all').then(res => {
        this.courses = res.data
      })
    },
    methods: {
      setCourse(event) {
        this.model.group.course = this.courses.find(c => c.id == event.target.value)
      },
      getGroup(id) {
        axios.get(`http://localhost:8081/api/groups/${id}`).then(res => {
          if (!res.data) {
            alert("Страница не найдена!")
            window.location.replace("/groups")
          }
          else {
            this.model.group = res.data
          }
        })
      },
      updateGroups() {
        if (this.v$.$invalid) {
          this.v$.$touch()
          this.alert = 'danger'
          return
        }

        axios.post(`http://localhost:8081/api/groups/${this.$route.params.id}`,
            this.model.group)
            .then(res => {
              this.model.group = res.data
              this.alert = 'success'
              this.v$.$reset()
            })
      }
    },
    setup: () => ({ v$: useVuelidate() }),
    validations() {
      return {
        model: {
          group: {
            name: { required },
            capacity: {required, integer, minValue: minValue(1)},
            course: {required}
          }
        }
      }
    }
  }
</script>
