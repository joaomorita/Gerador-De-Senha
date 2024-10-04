document.getElementById('gerarBtn').addEventListener('click', gerarSenha);

function gerarSenha() {
    fetch('http://localhost:8080/gerar-senha')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao gerar a senha');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('senha_display').textContent = data.senha;
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Não foi possível gerar a senha. Tente novamente mais tarde.');
        });
}