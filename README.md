# AI-agent
Repositório criado para documentar código da aula de Inteligência artificial



## Stage 1 - Agente reativo simples

O agente não mantém memória das posições já visitadas nem uma representação interna do ambiente.

Sua decisão de movimento é baseada apenas na percepção atual, isto é, na posição em que se encontra e na presença de limites do grid nas direções possíveis de deslocamento.

Nesta etapa, o ambiente é um grid vazio, sem obstáculos.

### PEAS do agente:

P - Performance:
- Visitar todos os quatro cantos do grid (obrigatório)
- Não sair dos limites do grid [9][9], [0][9], [9][0], [0][0]
- Não ficar preso em loop infinito
- Minimizar número de passos (desejável)
- Completar tarefa de forma autônoma

E - Environment:
- Grid 10x10
- Ambiente estático
- Sem nenhum obstáculo

A - Actuators:
- moverCima()
- moverBaixo()
- moverEsquerda()
- moverDireita()

S - Sensors:
- marcarCantoVisitado(x,y)


## Stage 2 - Agente Reativo Baseado em Modelo

### PEAS do agente:

P - Performance:
- Visitar os quatro cantos do grid
- Minimizar número de passos
- Não sair dos limites
- Evitar loops desnecessários

E - Environment:
- Grid 10x10
- Ambiente estático
- Sem obstáculos móveis

A - Actuators:
- moverCima()
- moverBaixo()
- moverEsquerda()
- moverDireita()

S - Sensors:
- lerPosicaoAtual()
- verificarSePodeMover(x, y)
- verificarSeEstaEmCanto(x, y)
- verificarCasasAdjacentes()

## Stage 3 - Agente baseado em objetivo

### PEAS do agente:

P – Performance (Desempenho)
-	Encontrar o menor caminho válido entre a posição inicial e a posição objetivo
-	Alcançar o objetivo de forma correta e completa
-	Evitar colisões com obstáculos
-	Não sair dos limites do grid
-	Não revisitar estados desnecessariamente (evitar loops)
-	Minimizar o número de movimentos realizados
-	Encerrar corretamente caso não exista caminho possível

E – Environment (Ambiente)
-	Grid 10x10
-	Ambiente estático e totalmente observável
-	Presença de obstáculos fixos (imutáveis)
-	Posição inicial e posição objetivo definidas pelo usuário
-	Ambiente discreto (movimentos em células)
-	Ambiente determinístico (mesma ação → mesmo resultado)


A – Actuators (Atuadores)
-	moverCima()
-	moverBaixo()
-	moverEsquerda()
-	moverDireita()

S – Sensors (Sensores)
-	Leitura da posição atual (x, y)
-	Verificação de células válidas dentro do grid
-	Detecção de obstáculos nas posições adjacentes
-	Identificação da posição objetivo
-	Mapeamento das células visitadas (controle interno)
-	Exploração das posições vizinhas durante a busca (BFS)