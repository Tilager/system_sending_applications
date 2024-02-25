<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        Авторизация
      </div>
      <div class="card-body">
        <div class="alert alert-danger" role="alert" v-if="alert === 'danger'">
          Проверьте все поля!
        </div>
        <div class="alert alert-danger" role="alert" v-if="alert === 'not found'">
          Пользователя с таким логином и паролем не существует!
        </div>
        <div class="mb-3">
          <label for="">Логин</label>
          <input v-model="model.user.login"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.user.login.$dirty && v$.model.user.login.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Пароль</label>
          <input v-model="model.user.password"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.user.password.$dirty && v$.model.user.password.$error)}">
        </div>
        <div class="mb-3">
          <button class="btn btn-primary" @click="login" type="button">Войти</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import useVuelidate from "@vuelidate/core";
  import {required} from "@vuelidate/validators";
  import {mapActions} from "vuex";

  export default {
    name: 'login',
    data() {
      return {
        alert: '',
        model: {
          user: {
            login: '',
            password: ''
          }
        }
      }
    },
    mounted() {
      if (Object.hasOwn(this.$route.query, 'logout')) {
        this.clearUser()
        alert("Вы успешно вышли из системы!")
      }
    },
    methods: {
      ...mapActions(['setUser', 'clearUser']),
      login() {
        if (this.v$.$invalid) {
          this.v$.$touch()
          this.alert = 'danger'
          return
        }

        axios.post("http://localhost:8081/auth/login", this.model.user)
            .then(res => {
              if (res.data) {
                this.setUser(res.data)

                if (res.data.type === 'user') {
                  window.location.replace('/application')
                } else if (res.data.type === 'admin') {
                  window.location.replace('/')
                }

                this.v$.$reset()
              } else {
                this.alert = 'not found'
              }

            }).catch(err => {
              alert(err.response.data)
            })
      }
    },
    setup: () => ({ v$: useVuelidate() }),
    validations() {
      return {
        model: {
          user: {
            login: { required },
            password: { required }
          }
        }
      }
    }
  }
</script>
