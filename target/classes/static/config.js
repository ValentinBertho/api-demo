/**
 * Configuration centralisée pour l'add-in ATHENEO
 *
 * Ce fichier contient toutes les configurations de l'application.
 * Modifier ce fichier pour adapter l'add-in à votre environnement.
 *
 * @version 1.0.0
 * @date 2025-01-28
 */

const ATHENEO_CONFIG = {
  /**
   * Configuration de l'API ATHENEO
   */
  api: {
    /**
     * URL de base de l'API ATHENEO
     * @type {string}
     * @example 'https://api.atheneo.com/v1'
     */
    baseUrl: 'https://api-demo-production-3184.up.railway.app/atheneo/api',

    /**
     * Clé API pour l'authentification
     * ⚠️ IMPORTANT : Ne jamais commiter une vraie clé API
     * Utiliser des variables d'environnement en production
     * @type {string}
     */
    apiKey: 'VotreCleAPISecrete123',

    /**
     * Timeout des requêtes HTTP en millisecondes
     * @type {number}
     * @default 10000
     */
    timeout: 10000,

    /**
     * Nombre de tentatives en cas d'échec
     * @type {number}
     * @default 2
     */
    retryAttempts: 2,

    /**
     * Délai entre les tentatives en millisecondes
     * Utilise un backoff exponentiel : delay * (attempt + 1)
     * @type {number}
     * @default 1000
     */
    retryDelay: 1000,

    /**
     * Liste des codes HTTP à ne pas réessayer
     * @type {number[]}
     */
    noRetryStatusCodes: [400, 401, 403, 404, 422],

    /**
     * Endpoints de l'API
     * @type {Object}
     */
    endpoints: {
      mails: '/mails',
      demandes: '/demandes',
      interlocuteurs: '/interlocuteurs',
      piecesJointes: '/pieces-jointes',
      actions: '/actions',
      analytics: '/analytics'
    }
  },

  /**
   * Configuration de l'interface utilisateur
   */
  ui: {
    /**
     * Durée d'affichage des messages de statut (ms)
     * @type {number}
     * @default 5000
     */
    statusMessageDuration: 5000,

    /**
     * Durée des animations (ms)
     * @type {number}
     * @default 300
     */
    animationDuration: 300,

    /**
     * Thème par défaut
     * @type {'dark'|'light'}
     * @default 'dark'
     */
    defaultTheme: 'dark',

    /**
     * Afficher les tooltips
     * @type {boolean}
     * @default true
     */
    showTooltips: true,

    /**
     * Afficher les descriptions des boutons
     * @type {boolean}
     * @default true
     */
    showButtonDescriptions: true,

    /**
     * Ouvrir le contexte email par défaut
     * @type {boolean}
     * @default false
     */
    contextOpenByDefault: false,

    /**
     * Mode compact (interface réduite)
     * @type {boolean}
     * @default false
     */
    compactMode: false
  },

  /**
   * Configuration des fonctionnalités
   */
  features: {
    /**
     * Activer les statistiques rapides
     * @type {boolean}
     * @default false
     */
    enableQuickStats: false,

    /**
     * Activer le mode hors ligne
     * @type {boolean}
     * @default false
     */
    enableOfflineMode: false,

    /**
     * Activer la sauvegarde automatique
     * @type {boolean}
     * @default false
     */
    autoSaveEnabled: false,

    /**
     * Délai de sauvegarde automatique (ms)
     * @type {number}
     * @default 300000
     */
    autoSaveDelay: 300000, // 5 minutes

    /**
     * Activer le cache API
     * @type {boolean}
     * @default true
     */
    enableApiCache: true,

    /**
     * Durée du cache API (ms)
     * @type {number}
     * @default 300000
     */
    apiCacheDuration: 300000, // 5 minutes

    /**
     * Activer les confirmations pour les actions importantes
     * @type {boolean}
     * @default false
     */
    confirmBeforeAction: false,

    /**
     * Activer la télémétrie (analytics)
     * @type {boolean}
     * @default true
     */
    enableTelemetry: true
  },

  /**
   * Configuration du logging
   */
  logging: {
    /**
     * Niveau de log
     * @type {'DEBUG'|'INFO'|'WARN'|'ERROR'|'NONE'}
     * @default 'INFO'
     */
    level: 'INFO',

    /**
     * Afficher les logs dans la console
     * @type {boolean}
     * @default true
     */
    console: true,

    /**
     * Envoyer les logs au serveur
     * @type {boolean}
     * @default false
     */
    remote: false,

    /**
     * Logger les appels API
     * @type {boolean}
     * @default true
     */
    logApiCalls: true,

    /**
     * Logger les erreurs utilisateur
     * @type {boolean}
     * @default true
     */
    logUserErrors: true,

    /**
     * Inclure les données sensibles dans les logs (⚠️ Danger)
     * @type {boolean}
     * @default false
     */
    includeSensitiveData: false
  },

  /**
   * Configuration de la validation
   */
  validation: {
    /**
     * Longueur maximale du sujet
     * @type {number}
     * @default 500
     */
    maxSubjectLength: 500,

    /**
     * Taille maximale du corps du mail (bytes)
     * @type {number}
     * @default 1000000
     */
    maxBodySize: 1000000, // 1MB

    /**
     * Taille maximale des pièces jointes (bytes)
     * @type {number}
     * @default 26214400
     */
    maxAttachmentSize: 26214400, // 25MB

    /**
     * Extensions de fichiers autorisées
     * @type {string[]}
     */
    allowedFileExtensions: [
      '.pdf', '.doc', '.docx', '.xls', '.xlsx', '.ppt', '.pptx',
      '.txt', '.csv', '.jpg', '.jpeg', '.png', '.gif', '.zip', '.rar'
    ],

    /**
     * Regex de validation d'email
     * @type {RegExp}
     */
    emailRegex: /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  },

  /**
   * Configuration du développement
   */
  development: {
    /**
     * Mode debug activé
     * @type {boolean}
     * @default false
     */
    debug: false,

    /**
     * Afficher les temps d'exécution
     * @type {boolean}
     * @default false
     */
    showPerformanceMetrics: false,

    /**
     * Simuler des erreurs aléatoires (pour tests)
     * @type {boolean}
     * @default false
     */
    simulateErrors: false,

    /**
     * Utiliser des données mockées
     * @type {boolean}
     * @default false
     */
    useMockData: false
  },

  /**
   * Configuration de sécurité
   */
  security: {
    /**
     * Activer l'authentification OAuth
     * @type {boolean}
     * @default false
     */
    useOAuth: false,

    /**
     * URL du serveur OAuth
     * @type {string}
     */
    oauthUrl: '',

    /**
     * Client ID OAuth
     * @type {string}
     */
    oauthClientId: '',

    /**
     * Sanitizer le HTML des emails
     * @type {boolean}
     * @default true
     */
    sanitizeHtml: true,

    /**
     * Activer la détection XSS
     * @type {boolean}
     * @default true
     */
    xssProtection: true
  },

  /**
   * Messages personnalisables
   */
  messages: {
    errors: {
      network: 'Erreur de connexion réseau. Vérifiez votre connexion Internet.',
      unauthorized: 'Session expirée. Veuillez vous reconnecter.',
      forbidden: 'Vous n\'avez pas l\'autorisation d\'effectuer cette action.',
      notFound: 'Ressource non trouvée.',
      serverError: 'Erreur serveur. Veuillez réessayer ultérieurement.',
      unknown: 'Une erreur inattendue s\'est produite.'
    },
    success: {
      mailSaved: 'Mail enregistré avec succès',
      requestCreated: 'Demande créée avec succès',
      actionCreated: 'Action créée avec succès',
      attachmentsSaved: 'Pièces jointes enregistrées avec succès',
      contactOpened: 'Fiche contact ouverte'
    },
    warnings: {
      noAttachments: 'Aucune pièce jointe à enregistrer',
      contactNotFound: 'Contact non trouvé dans ATHENEO',
      offlineMode: 'Mode hors ligne : les modifications seront synchronisées ultérieurement'
    }
  },

  /**
   * Métadonnées de l'application
   */
  app: {
    /**
     * Nom de l'application
     * @type {string}
     */
    name: 'ATHENEO Add-in',

    /**
     * Version de l'application
     * @type {string}
     */
    version: '1.0.0',

    /**
     * Environnement
     * @type {'development'|'staging'|'production'}
     */
    environment: 'production',

    /**
     * URL de support
     * @type {string}
     */
    supportUrl: 'https://support.atheneo.fr',

    /**
     * URL de documentation
     * @type {string}
     */
    docsUrl: 'https://docs.atheneo.fr/add-in-outlook'
  }
};

/**
 * Fonction pour récupérer une valeur de configuration
 * @param {string} path - Chemin vers la valeur (ex: 'api.timeout')
 * @param {*} defaultValue - Valeur par défaut si non trouvée
 * @returns {*} La valeur de configuration
 *
 * @example
 * const timeout = getConfig('api.timeout', 5000);
 * const theme = getConfig('ui.defaultTheme', 'light');
 */
function getConfig(path, defaultValue = null) {
  const keys = path.split('.');
  let value = ATHENEO_CONFIG;

  for (const key of keys) {
    if (value && typeof value === 'object' && key in value) {
      value = value[key];
    } else {
      return defaultValue;
    }
  }

  return value;
}

/**
 * Fonction pour mettre à jour une valeur de configuration
 * @param {string} path - Chemin vers la valeur
 * @param {*} value - Nouvelle valeur
 *
 * @example
 * setConfig('ui.defaultTheme', 'dark');
 * setConfig('api.timeout', 15000);
 */
function setConfig(path, value) {
  const keys = path.split('.');
  const lastKey = keys.pop();
  let obj = ATHENEO_CONFIG;

  for (const key of keys) {
    if (!(key in obj) || typeof obj[key] !== 'object') {
      obj[key] = {};
    }
    obj = obj[key];
  }

  obj[lastKey] = value;

  // Sauvegarder dans localStorage si disponible
  if (typeof localStorage !== 'undefined') {
    try {
      localStorage.setItem(`atheneo_config_${path}`, JSON.stringify(value));
    } catch (e) {
      console.warn('Failed to save config to localStorage:', e);
    }
  }
}

/**
 * Fonction pour charger les configurations personnalisées depuis localStorage
 */
function loadCustomConfig() {
  if (typeof localStorage === 'undefined') return;

  try {
    const customConfig = localStorage.getItem('atheneo_custom_config');
    if (customConfig) {
      const parsed = JSON.parse(customConfig);
      Object.assign(ATHENEO_CONFIG, parsed);
    }
  } catch (e) {
    console.warn('Failed to load custom config:', e);
  }
}

/**
 * Fonction pour sauvegarder les configurations personnalisées
 */
function saveCustomConfig() {
  if (typeof localStorage === 'undefined') return;

  try {
    localStorage.setItem('atheneo_custom_config', JSON.stringify(ATHENEO_CONFIG));
  } catch (e) {
    console.warn('Failed to save custom config:', e);
  }
}

/**
 * Fonction pour réinitialiser la configuration par défaut
 */
function resetConfig() {
  if (typeof localStorage !== 'undefined') {
    try {
      // Supprimer toutes les clés de config
      const keys = Object.keys(localStorage);
      keys.forEach(key => {
        if (key.startsWith('atheneo_config_')) {
          localStorage.removeItem(key);
        }
      });
      localStorage.removeItem('atheneo_custom_config');
    } catch (e) {
      console.warn('Failed to reset config:', e);
    }
  }

  // Recharger la page pour appliquer les configs par défaut
  if (typeof window !== 'undefined') {
    window.location.reload();
  }
}

// Charger les configurations personnalisées au démarrage
if (typeof document !== 'undefined') {
  document.addEventListener('DOMContentLoaded', loadCustomConfig);
}

// Export pour utilisation dans d'autres fichiers
if (typeof module !== 'undefined' && module.exports) {
  module.exports = {
    ATHENEO_CONFIG,
    getConfig,
    setConfig,
    loadCustomConfig,
    saveCustomConfig,
    resetConfig
  };
}
