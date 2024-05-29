function parseJwt(token) {
	const base64Url = token.split('.')[1]
	const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
	const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
		return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
	}).join(''))

	return JSON.parse(jsonPayload)
}

function formatarData(data) {
	const dataObj = new Date(data)
	const dia = String(dataObj.getDate()).padStart(2, '0')
	const mes = String(dataObj.getMonth() + 1).padStart(2, '0')
	const ano = dataObj.getFullYear()
	return `${dia}/${mes}/${ano}`
}

function login(event) {
	event.preventDefault()

	const email = document.getElementById('email').value
	const senha = document.getElementById('senha').value

	const login = JSON.stringify({
		"email": email,
		"senha": senha,
	})

	const requestOptions = {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: login,
		redirect: "follow"
	}

	fetch("http://localhost:8080/apis/security/logar", requestOptions)
		.then((response) => response.text())
		.then((result) => {
			const { nivel } = parseJwt(result)

			console.log(nivel)
			if (nivel == 1) {
				window.location.href = "Administracao.html"
			}
			else {
				window.location.href = "Denuncias.html"
			}
		})
}

function atualizarListaOrgaos() {
	fetch("http://localhost:8080/apis/adm/get-all-orgaos", { method: 'GET' })
		.then((response) => response.json())
		.then((result) => {
			const listaOrgaos = document.getElementById("listaOrgaos")

			let orgaosHTML = ""
			result.forEach(item => {
				orgaosHTML += `
					<div class="bg-gray-100 p-4 border rounded shadow flex justify-between items-center mb-4">
						<p class="text-lg font-semibold">${item.nome}</p>
						<div>
							<button onclick="alterarOrgao(${item.id})"
								class="bg-green-500 text-white px-3 py-1 rounded mr-2">Alterar</button>
							<button onclick="excluirOrgao(${item.id})"
								class="bg-red-500 text-white px-3 py-1 rounded">Excluir</button>
						</div>
					</div>
				`
			})

			listaOrgaos.innerHTML = orgaosHTML
		})
}

function criarOrgao(event) {
	event.preventDefault()
	const nome = document.getElementById('nomeOrgao').value

	const orgao = JSON.stringify({
		"nome": nome
	})

	const requestOptions = {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: orgao,
		redirect: "follow"
	}

	fetch("http://localhost:8080/apis/adm/add-orgao", requestOptions)
		.then(() => atualizarListaOrgaos())
}

function abrirModal(id, nome) {
	document.getElementById('editOrgaoId').value = id
	document.getElementById('editNomeOrgao').value = nome
	document.getElementById('editOrgaoModal').classList.remove('hidden')
}

function fecharModal() {
	document.getElementById('editOrgaoModal').classList.add('hidden')
}

function alterarOrgao(id) {
	fetch(`http://localhost:8080/apis/adm/get-orgao?id=${id}`)
		.then(response => response.json())
		.then(data => {
			abrirModal(data.id, data.nome)
		})
		.catch(error => console.error('Erro ao buscar órgão:', error))
}

function salvarAlteracaoOrgao(event) {
	event.preventDefault()
	const id = document.getElementById('editOrgaoId').value
	const nome = document.getElementById('editNomeOrgao').value

	const orgaoAtualizado = JSON.stringify({ "nome": nome })

	const requestOptions = {
		method: "PUT",
		headers: {
			"Content-Type": "application/json",
		},
		body: orgaoAtualizado,
		redirect: "follow"
	}

	fetch(`http://localhost:8080/apis/adm/update-orgao/${id}`, requestOptions)
		.then(response => {
			if (!response.ok) {
				throw new Error('Erro ao atualizar órgão')
			}
			return response.json()
		})
		.then(data => {
			fecharModal()
			atualizarListaOrgaos()
		})
		.catch(error => console.error('Erro ao salvar alterações:', error))
}

function excluirOrgao(id) {
	fetch(`http://localhost:8080/apis/adm/delete-orgao?id=${id}`, { method: 'DELETE' })
		.then(response => {
			if (!response.ok) {
				throw new Error('Erro ao excluir órgão');
			}
		})
		.then(() => {
			atualizarListaOrgaos();
		})
		.catch(error => console.error('Erro ao excluir órgão:', error));
}

function atualizarListaTipoProblemas() {
	fetch("http://localhost:8080/apis/adm/get-all-tipos", { method: 'GET' })
		.then((response) => response.json())
		.then((result) => {
			const listaProblemas = document.getElementById("listaProblemas")

			let problemasHTML = ""
			result.forEach(item => {
				problemasHTML += `
					<div class="bg-gray-100 p-4 border rounded shadow flex justify-between items-center mb-4">
						<p class="text-lg font-semibold">${item.nome}</p>
						<div>
							<button onclick="alterarProblema(${item.id})"
								class="bg-green-500 text-white px-3 py-1 rounded mr-2">Alterar</button>
							<button onclick="excluirProblema(${item.id})"
								class="bg-red-500 text-white px-3 py-1 rounded">Excluir</button>
						</div>
					</div>
				`
			})

			listaProblemas.innerHTML = problemasHTML
		})
}

function criarProblema(event) {
	event.preventDefault()
	const nome = document.getElementById('nomeProblema').value

	const orgao = JSON.stringify({
		"nome": nome
	})

	const requestOptions = {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: orgao,
		redirect: "follow"
	}

	fetch("http://localhost:8080/apis/adm/add-Tipo", requestOptions)
		.then(() => atualizarListaTipoProblemas())
}

function alterarProblema(id) {
	fetch(`http://localhost:8080/apis/adm/get-Tipo?id=${id}`)
		.then(response => response.json())
		.then(data => {
			abrirModalProblema(data.id, data.nome)
		})
		.catch(error => console.error('Erro ao buscar tipo de problema:', error))
}

function abrirModalProblema(id, nome) {
	document.getElementById('editProblemaId').value = id
	document.getElementById('editNomeProblema').value = nome
	document.getElementById('editProblemaModal').classList.remove('hidden')
}

function fecharModalProblema() {
	document.getElementById('editProblemaModal').classList.add('hidden')
}

function salvarAlteracaoProblema(event) {
	event.preventDefault()
	const id = document.getElementById('editProblemaId').value
	const nome = document.getElementById('editNomeProblema').value

	const problemaAtualizado = JSON.stringify({ "nome": nome })

	const requestOptions = {
		method: "PUT",
		headers: {
			"Content-Type": "application/json",
		},
		body: problemaAtualizado,
		redirect: "follow"
	}

	fetch(`http://localhost:8080/apis/adm/update-tipo/${id}`, requestOptions)
		.then(response => {
			if (!response.ok) {
				throw new Error('Erro ao atualizar tipo de problema')
			}
			return response.json()
		})
		.then(data => {
			fecharModalProblema()
			atualizarListaTipoProblemas()
		})
		.catch(error => console.error('Erro ao salvar alterações:', error))
}

function excluirProblema(id) {
	fetch(`http://localhost:8080/apis/adm/delete-Tipo?id=${id}`, { method: 'DELETE' })
		.then(response => {
			if (!response.ok) {
				throw new Error('Erro ao excluir tipo de problema')
			}
		})
		.then(() => {
			atualizarListaTipoProblemas()
		})
		.catch(error => console.error('Erro ao excluir tipo de problema:', error))
}

function atualizarListaDenuncias() {
	fetch("http://localhost:8080/apis/adm/get-all-denuncias", { method: 'GET' })
		.then((response) => response.json())
		.then((result) => {
			const listaDenuncias = document.getElementById("denuncias")

			let denunciasHTML = ""
			result.forEach((item) => {
				denunciasHTML += `
					<div class="bg-gray-100 p-4 border rounded shadow mb-4">
						<p class="text-lg font-semibold">${item.titulo}</p>
						<p><strong>Texto:</strong> ${item.texto}</p>
						<p><strong>Urgência:</strong> ${item.urgencia}</p>
						<p><strong>Data:</strong> ${formatarData(item.data)}</p>
						<p><strong>Órgão:</strong> ${item.orgao.nome}</p>
						<p><strong>Tipo:</strong> ${item.tipo.nome}</p>
						<p><strong>Usuário:</strong> ${item.usuario.email}</p>
						<div>
							<button onclick="darFeedback(${item.id})"
								class="bg-yellow-500 text-white px-3 py-1 rounded mr-2">Dar Feedback</button>
							<button onclick="excluirDenuncia(${item.id})"
								class="bg-red-500 text-white px-3 py-1 rounded">Excluir</button>
						</div>
					</div>
				`
			})

			listaDenuncias.innerHTML = denunciasHTML
		})
}

function criarDenuncia(event) { }

function darFeedback(id) {
	console.log(id)
}

function excluirDenuncia(id) {
	fetch(`http://localhost:8080/apis/adm/delete-denuncia?id=${id}`, { method: 'DELETE' })
		.then(response => {
			if (!response.ok) {
				throw new Error('Erro ao excluir denúncia')
			}
		})
		.then(() => {
			atualizarListaDenuncias()
		})
		.catch(error => console.error('Erro ao excluir denúncia:', error))
}