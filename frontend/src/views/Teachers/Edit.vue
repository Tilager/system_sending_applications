<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        Добавление учителя
      </div>
      <div class="card-body">
        <div class="alert alert-success" role="alert" v-if="alert === 'success'">
          Данные успешно добавлены!
        </div>
        <div class="alert alert-danger" role="alert" v-if="alert === 'danger'">
          Проверьте все поля!
        </div>

        <div class="mb-3">
          <label for="">Фамилия</label>
          <input v-model="model.teacher.surname"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.teacher.surname.$dirty && v$.model.teacher.surname.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Имя</label>
          <input v-model="model.teacher.name"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.teacher.name.$dirty && v$.model.teacher.name.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Отчество</label>
          <input v-model="model.teacher.patronymic"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.teacher.patronymic.$dirty && v$.model.teacher.patronymic.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Номер телефона</label>
          <input v-model="model.teacher.phone"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.teacher.phone.$dirty && v$.model.teacher.phone.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Почта</label>
          <input v-model="model.teacher.email"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.teacher.email.$dirty && v$.model.teacher.email.$error)}">
        </div>
        <div class="mb-3">
          <button class="btn btn-primary" @click="saveTeacher" type="button">Сохранить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import useVuelidate from "@vuelidate/core";
  import {email, required} from "@vuelidate/validators";

  export default {
    name: 'teachersCreate',
    data() {
      return {
        alert: '',
        model: {
          teacher: {
            name: '',
            surname: '',
            patronymic: '',
            phone: '',
            email: ''
          }
        }
      }
    },
    methods: {
      saveTeacher() {
        if (this.v$.$invalid) {
          this.v$.$touch()
          this.alert = 'danger'
          return
        }

        axios.post("http://localhost:8081/api/createTeacher",
            this.model.teacher)
            .then(res => {
              this.model.teacher = {
                name: '',
                surname: '',
                patronymic: '',
                phone: '',
                email: ''
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
          teacher: {
            name: { required },
            surname: { required },
            patronymic: { required },
            phone: { required, phone: value => {
                return /^(^8|7|\+7)((\d{10})|(\s\(\d{3}\)\s\d{3}\s\d{2}\s\d{2}))$/.test(value)
              }},
            email: { required, email }
          }
        }
      }
    }
  }
</script>
