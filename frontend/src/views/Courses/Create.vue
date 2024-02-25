<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        Добавление курса
      </div>
      <div class="card-body">
        <div class="alert alert-success" role="alert" v-if="alert === 'success'">
          Данные успешно добавлены!
        </div>
        <div class="alert alert-danger" role="alert" v-if="alert === 'danger'">
          Проверьте все поля!
        </div>

        <div class="mb-3">
          <label for="">Название</label>
          <input v-model="model.course.name"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.course.name.$dirty && v$.model.course.name.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Описание</label>
          <input v-model="model.course.description"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.course.description.$dirty && v$.model.course.description.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Язык</label>
          <input v-model="model.course.language"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.course.language.$dirty && v$.model.course.language.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Длительность (Ч)</label>
          <input v-model="model.course.duration"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.course.duration.$dirty && v$.model.course.duration.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Цена</label>
          <input v-model="model.course.price"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.course.price.$dirty && v$.model.course.price.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Преподаватель</label>

          <select class="form-control"
                  :class="{'is-invalid': (v$.model.course.teacher.$dirty && v$.model.course.teacher.$error)}"
                  @change="setTeacher"
          >
            <option disabled selected value> Выберите преподавателя </option>
            <option v-for="teacher in teachers"
                    :value="teacher.id"> {{ teacher.surname }} {{ teacher.name }} {{ teacher.patronymic }} </option>
          </select>
        </div>
        <div class="mb-3">
          <button class="btn btn-primary" @click="saveCourse" type="button">Сохранить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import useVuelidate from "@vuelidate/core";
  import {email, integer, minValue, numeric, required} from "@vuelidate/validators";
  import axios from "axios";

  export default {
    name: 'teacherCreate',
    data() {
      return {
        alert: '',
        model: {
          course: {
            name: '',
            description: '',
            language: '',
            duration: null,
            price: null,
            teacher: null
          }
        }
      }
    },
    mounted() {

    },
    methods: {
      setTeacher(event) {
        this.model.course.teacher = this.teachers.find(t => t.id == event.target.value)
      },
      saveCourse() {
        if (this.v$.$invalid) {
          this.v$.$touch()
          this.alert = 'danger'
          return
        }

        axios.post("http://localhost:8081/api/courses/create",
            this.model.course)
            .then(res => {
              this.model.course = {
                name: '',
                description: '',
                language: '',
                duration: 0,
                price: 0,
                teacher: null
              }

              this.alert = 'success'
              this.v$.$reset()
            })
      }
    },
    setup: () => ({ v$: useVuelidate() }),
    validations() {
      return {
        model: {
          course: {
            name: { required },
            description: { required },
            language: { required },
            duration: {required, integer, minValue: minValue(1)},
            price: {required, numeric, minValue: minValue(0)},
            teacher: {required}
          }
        }
      }
    }
  }
</script>
