export const environment = {
    production: true,
    apiUrl: location.href.split('/').reverse().splice(2).reverse().join('/') // parent context when Embedded webapp
};
