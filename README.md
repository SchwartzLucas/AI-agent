# AI-agent
Repositório criado para documentar código da aula de Inteligência artificial



## Stage 1 - Agente reativo simples

O agente não mantém memória das posições já visitadas nem uma representação interna do ambiente.

Sua decisão de movimento é baseada apenas na percepção atual, isto é, na posição em que se encontra e na presença de limites do grid nas direções possíveis de deslocamento.

Nesta etapa, o ambiente é um grid vazio, sem obstáculos.

### PEAS:

P - performance:
- Visitar todos os quatro cantos do grid (obrigatório)
- Não sair dos limites do grid [9][9], [0][9], [9][0], [0][0]
- Não ficar preso em loop infinito
- Minimizar número de passos (desejável)
- Completar tarefa de forma autônoma

Métrica principal: Todos cantos visitados = SUCESSO

Métrica secundária: Passos totais (menor = melhor)



## Stage 2 - Agente Reativo Baseado em Modelo

### Peas do agente:

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